package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.Manifest
import android.os.Bundle
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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.feature.dashboard.R
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentHomeBinding
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.RuasJalan
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters.NewsPagerAdapter
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.HomeViewModel
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store.HomeAction
import id.go.jabarprov.dbmpr.utils.extensions.capitalizeEachWord
import id.go.jabarprov.dbmpr.utils.extensions.checkPermission
import id.go.jabarprov.dbmpr.utils.extensions.showToast
import id.go.jabarprov.dbmpr.utils.utils.LocationUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import widget_utils.HorizontalMarginItemDecoration

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

    private val newsPagerAdapter by lazy { NewsPagerAdapter() }

    private val locationUtils by lazy { LocationUtils(requireActivity()) }

    private var job: Job? = null

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
            viewPagerNews.apply {
                adapter = newsPagerAdapter
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
        }

        setVisibilityCekLokasi(true)
        setupCarousel()
        setUpInfiniteOnPageListener(5)
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
        binding.viewPagerNews.setCurrentItem(1, false)

        job = startAutomaticScroll()
    }

    private fun startAutomaticScroll(): Job {
        return viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                while (true) {
                    delay(5000)
                    binding.viewPagerNews.currentItem += 1
                }
            }
        }
    }

    private fun setUpInfiniteOnPageListener(listSize: Int) {
        val newListSize = listSize + 2
        binding.viewPagerNews.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        job?.cancel()
                        job = startAutomaticScroll()
                        when (binding.viewPagerNews.currentItem) {
                            newListSize - 1 -> binding.viewPagerNews.setCurrentItem(1, false)
                            0 -> binding.viewPagerNews.setCurrentItem(newListSize - 2, false)
                        }
                    }
                }
            })
        }
    }

    private fun observeHomeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeViewModel.uiState.collect {
                    processSlideNewsState(it.listSlideNewsState)
                    processNearbyRuasJalanState(it.nearbyRuasJalanState)
                }
            }
        }
    }

    private fun processSlideNewsState(state: Resource<List<News>>) {
        when (state) {
            is Resource.Success -> {
                newsPagerAdapter.submitList(state.data)
                binding.viewPagerNews.setCurrentItem(1, false)
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

}