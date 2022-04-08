package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import id.go.jabarprov.dbmpr.feature.dashboard.R
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentHomeBinding
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters.NewsPagerAdapter
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.models.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import widget_utils.HorizontalMarginItemDecoration

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val newsPagerAdapter by lazy { NewsPagerAdapter(LIST_OF_NEWS) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        binding.apply {
            viewPagerNews.apply {
                adapter = newsPagerAdapter
            }
        }

        setupCarousel()
        setUpInfiniteOnPageListener(LIST_OF_NEWS.size)
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

        viewLifecycleOwner.lifecycleScope.launch {
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
                        when (binding.viewPagerNews.currentItem) {
                            listSize - 1 -> binding.viewPagerNews.setCurrentItem(1, false)
                            0 -> binding.viewPagerNews.setCurrentItem(newListSize - 2, false)
                        }
                    }
                }
            })
        }
    }

    companion object {
        val LIST_OF_NEWS = listOf(
            News(
                "Berita 1",
                "-"
            ),
            News(
                "Berita 2",
                "-"
            ),
            News(
                "Berita 3",
                "-"
            ),
            News(
                "Berita 4",
                "-"
            ),
            News(
                "Berita 5",
                "-"
            ),
        )
    }

}