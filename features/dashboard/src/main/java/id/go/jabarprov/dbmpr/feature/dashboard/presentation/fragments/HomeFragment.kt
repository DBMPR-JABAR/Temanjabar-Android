package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.common.presentation.widget_utils.HorizontalMarginItemDecoration
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.feature.dashboard.R
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentHomeBinding
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters.DashboardMenuAdapter
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters.NewsAdapter
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters.SliderAdapter
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.models.DashboardMenuModel
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.HomeViewModel
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store.HomeAction
import id.go.jabarprov.dbmpr.utils.extensions.capitalizeEachWord
import id.go.jabarprov.dbmpr.utils.extensions.checkPermission
import id.go.jabarprov.dbmpr.utils.extensions.showToast
import id.go.jabarprov.dbmpr.utils.utils.LocationUtils
import kotlinx.coroutines.launch

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

    private val sliderAdapter by lazy { SliderAdapter() }

    private val newsAdapter by lazy { NewsAdapter() }

    private val locationUtils by lazy { LocationUtils(requireActivity()) }

    private val dashboardMenuAdapter by lazy {
        DashboardMenuAdapter(4, 0).also {
            it.submitList(LIST_MENU_DASHBOARD)
        }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getNearbyLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getNearbyLocation()
                }
                else -> {
                    showToast("Izin Lokasi Ditolak")
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        observeHomeState()
        getSliderNews()
    }

    private fun getSliderNews() {
        homeViewModel.processAction(HomeAction.GetSliderNews)
    }

    private fun initUI() {
        binding.apply {
            root.isEnabled = false

            viewPagerNews.apply {
                adapter = sliderAdapter.apply {
                    setOnClickListener {
                        if (it.slug == "laporan-masyarakat") {
                            val request = NavDeepLinkRequest
                                .Builder
                                .fromUri("https://temanjabar.dbmpr.jabarprov.go.id/webview?url=https://tj.temanjabar.net#laporan".toUri())
                                .build()
                            findNavController().navigate(request)
                        } else {
                            val request = NavDeepLinkRequest
                                .Builder
                                .fromUri("https://temanjabar.dbmpr.jabarprov.go.id/news?slug=${it.slug}".toUri())
                                .build()
                            findNavController().navigate(request)
                        }
                    }
                }
            }

            recyclerViewMenu.apply {
                adapter = dashboardMenuAdapter
                layoutManager = GridLayoutManager(requireContext(), 4)
                setHasFixedSize(true)
            }

            recyclerViewNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

            buttonCekLokasi.setOnClickListener {
                if (!checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION) || !checkPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                } else {
                    getNearbyLocation()
                }
            }

            buttonPerbaruiLokasi.setOnClickListener {
                getNearbyLocation()
            }

            buttonLihatLokasiPeta.setOnClickListener {
                val currentLat = homeViewModel.uiState.value.currentLat
                val currentLong = homeViewModel.uiState.value.currentLong
                val request =
                    NavDeepLinkRequest
                        .Builder
                        .fromUri("https://temanjabar.dbmpr.jabarprov.go.id/map?lat=${currentLat}&long=${currentLong}".toUri())
                        .build()
                findNavController().navigate(request)
            }

            dotIndicator.attachTo(viewPagerNews)
        }

        setVisibilityCekLokasi(true)
        setupCarousel()
    }

    private fun setVisibilityLoadingLokasi(isVisible: Boolean) {
        binding.shimmerFrameLayoutLoadingLokasi.apply {
            visibility = if (isVisible) {
                startShimmer()
                View.VISIBLE
            } else {
                stopShimmer()
                View.GONE
            }
        }
    }

    private fun setVisibilityCekLokasi(isVisible: Boolean) {
        binding.constraintLayoutCekLokasi.apply {
            this.isVisible = isVisible
        }
    }

    private fun setVisibilityLokasiSaatIni(isVisible: Boolean) {
        binding.constraintLayoutCurrentLokasi.apply {
            this.isVisible = isVisible
        }
    }

    private fun getNearbyLocation() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.processAction(HomeAction.GetLocation)
            if (!locationUtils.isLocationEnabled()) {
                locationUtils.enableLocationService()
            }
            val location = locationUtils.getCurrentLocation(CancellationTokenSource().token)
            if (location == null) {
                homeViewModel.processAction(HomeAction.GetLocationFailed("Tidak Dapat Mengambil Lokasi"))
            } else {
                homeViewModel.processAction(
                    HomeAction.GetNearbyRuasJalan(
                        location.latitude,
                        location.longitude
                    )
                )
            }
        }
    }

    private fun setupCarousel() {
        binding.viewPagerNews.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
        }
        binding.viewPagerNews.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPagerNews.addItemDecoration(itemDecoration)
    }

    private fun observeHomeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeViewModel.uiState.collect {
                    processSlideNewsState(it.listSlideNewsState)
                    processNearbyRuasJalanState(it.nearbyRuasJalanState)
                    processNewsState(it.listSlideNewsState)
                }
            }
        }
    }

    private fun processSlideNewsState(state: Resource<List<News>>) {
        when (state) {
            is Resource.Success -> {
                sliderAdapter.submitList(
                    state.data + listOf(
                        News(
                            id = 999,
                            title = "",
                            content = "",
                            imageUrl = "https://tj.temanjabar.net/assets/images/Untitled-1.png",
                            slug = "laporan-masyarakat"
                        )
                    )
                )
            }
        }
    }

    private fun processNearbyRuasJalanState(state: Resource<RuasJalan>) {
        when (state) {
            is Resource.Initial -> {
                setVisibilityCekLokasi(true)
                setVisibilityLoadingLokasi(false)
                setVisibilityLokasiSaatIni(false)
            }
            is Resource.Failed -> {
                setVisibilityCekLokasi(true)
                setVisibilityLoadingLokasi(false)
                setVisibilityLokasiSaatIni(false)
                showToast(state.errorMessage)
            }
            is Resource.Loading -> {
                setVisibilityCekLokasi(false)
                setVisibilityLoadingLokasi(true)
                setVisibilityLokasiSaatIni(false)
            }
            is Resource.Success -> {
                binding.apply {
                    textViewCurrentLokasiRuasJalan.text = state.data.nama
                    textViewCurrentLokasiKota.text = state.data.kota.capitalizeEachWord()
                }

                setVisibilityCekLokasi(false)
                setVisibilityLoadingLokasi(false)
                setVisibilityLokasiSaatIni(true)
            }
        }
    }

    private fun processNewsState(state: Resource<List<News>>) {
        binding.apply {
            when (state) {
                is Resource.Failed -> {
                    showToast("ERROR LOADING NEWS ${state.errorMessage}")
                }
                is Resource.Success -> {
                    layoutPlaceholderNews.root.isVisible = false
                    recyclerViewNews.isVisible = true
                    newsAdapter.submitList(state.data)

                }
                else -> {
                    layoutPlaceholderNews.root.isVisible = true
                    recyclerViewNews.isVisible = false
                }
            }
        }
    }

    companion object {
        private val LIST_MENU_DASHBOARD = listOf(
            DashboardMenuModel(
                "Peta DBMPR",
                id.go.jabarprov.dbmpr.common.R.drawable.ic_map
            ),
            DashboardMenuModel(
                "Berita DBMPR",
                id.go.jabarprov.dbmpr.common.R.drawable.ic_post
            ),
            DashboardMenuModel(
                "Response Laporan",
                id.go.jabarprov.dbmpr.common.R.drawable.ic_half_opened_mail
            ),
            DashboardMenuModel(
                "Menu Lainnya",
                id.go.jabarprov.dbmpr.common.R.drawable.ic_menu
            ),
        )
    }

}