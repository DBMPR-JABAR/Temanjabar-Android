package id.go.jabarprov.dbmpr.feature.news.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.core_main.Resource
import id.go.jabarprov.dbmpr.core_views.R
import id.go.jabarprov.dbmpr.feature.news.databinding.ActivityNewsBinding
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.NewsViewModel
import id.go.jabarprov.dbmpr.feature.news.presentation.viewmodels.store.NewsAction
import id.go.jabarprov.dbmpr.temanjabar.navigation.news.NewsArgs
import kotlinx.coroutines.launch

private const val TAG = "NewsActivity"

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val newsViewModel by viewModels<NewsViewModel>()

    private val binding by lazy { ActivityNewsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.statusBarColor =
            ContextCompat.getColor(this, android.R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }

        setContentView(binding.root)

        initUI()
        observeNewsState()
        getNews()
    }

    private fun initUI() {
        with(binding) {
            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun getNews() {
        intent.getStringExtra(NewsArgs.SLUG)?.let {
            newsViewModel.processAction(NewsAction.GetNews(it))
        }
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


    private fun observeNewsState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
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
        window?.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onDestroy()
    }
}