package id.go.jabarprov.dbmpr.feature.news.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_views.R
import id.go.jabarprov.dbmpr.feature.news.databinding.FragmentNewsBinding
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.NewsViewModel
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store.NewsAction
import kotlinx.coroutines.launch

private const val TAG = "NewsFragment"

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val newsViewModel by viewModels<NewsViewModel>()

    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        observeNewsState()

        getNews()
    }

    private fun initUI() {

    }

    private fun getNews() {
        newsViewModel.processAction(NewsAction.GetNews(arguments?.get("slug") as String))
    }

    private fun setVisibilityLoadingNews(isVisible: Boolean) {
        binding.shimmerFrameLayoutContent.apply {
            visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    private fun setVisibilityContentNews(isVisible: Boolean) {
        binding.apply {
            textViewDescription.isVisible = isVisible
            textViewTitle.isVisible = isVisible
            webviewContent.isVisible = isVisible
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), android.R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(activity?.window!!, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun observeNewsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                newsViewModel.uiState.collect {
                    processNewsState(it.news)
                }
            }
        }
    }

    private fun processNewsState(newsState: Resource<News>) {
        when (newsState) {
            is Resource.Failed -> {
                setVisibilityLoadingNews(false)
                setVisibilityContentNews(false)
            }
            is Resource.Initial -> {
                setVisibilityLoadingNews(false)
                setVisibilityContentNews(false)
            }
            is Resource.Loading -> {
                setVisibilityLoadingNews(true)
                setVisibilityContentNews(false)
            }
            is Resource.Success -> {
                setVisibilityLoadingNews(false)
                setVisibilityContentNews(true)
                renderContent(newsState.data)
                Log.d(TAG, "processNewsState: ${newsState.data}")
            }
        }
    }

    private fun renderContent(news: News) {
        binding.apply {
            imageViewNewsImage.load(news.imageUrl)
            textViewTitle.text = news.title
            textViewDescription.text = news.shortDescription
            webviewContent.loadData(news.content, "text/html", "UTF-8")
        }
    }

    override fun onDestroy() {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        WindowCompat.setDecorFitsSystemWindows(activity?.window!!, true)
        super.onDestroy()
    }
}