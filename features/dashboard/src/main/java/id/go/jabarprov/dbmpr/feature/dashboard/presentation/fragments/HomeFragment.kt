package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.dashboard.R
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentHomeBinding
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters.NewsPagerAdapter
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.HomeViewModel
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.viewmodels.home.store.HomeAction
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import widget_utils.HorizontalMarginItemDecoration

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var binding: FragmentHomeBinding

    private val newsPagerAdapter by lazy { NewsPagerAdapter(LIST_OF_NEWS) }

    private var job: Job? = null

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
        }

        setVisibilityCekLokasi(true)
        setupCarousel()
        setUpInfiniteOnPageListener(LIST_OF_NEWS.size)
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

    private fun setupCarousel() {

        binding.viewPagerNews.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.alpha = 0.25f + (1 - kotlin.math.abs(position))
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

                }
            }
        }
    }

    companion object {
        val LIST_OF_NEWS = listOf(
            News(
                id = 0,
                title = "-",
                content = "-",
                imageUrl = "https://images.unsplash.com/photo-1558637845-c8b7ead71a3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1632&q=80"
            ),
            News(
                id = 0,
                title = "-",
                content = "-",
                imageUrl = "https://images.unsplash.com/photo-1558637845-c8b7ead71a3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1632&q=80"
            ),
            News(
                id = 0,
                title = "-",
                content = "-",
                imageUrl = "https://images.unsplash.com/photo-1558637845-c8b7ead71a3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1632&q=80"
            ),
            News(
                id = 0,
                title = "-",
                content = "-",
                imageUrl = "https://images.unsplash.com/photo-1558637845-c8b7ead71a3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1632&q=80"
            ),
            News(
                id = 0,
                title = "-",
                content = "-",
                imageUrl = "https://images.unsplash.com/photo-1558637845-c8b7ead71a3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1632&q=80"
            )
        )
    }

}