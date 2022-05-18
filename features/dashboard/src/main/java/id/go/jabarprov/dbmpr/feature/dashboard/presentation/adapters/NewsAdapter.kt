package id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.LayoutNewsItemBinding
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.diff_utils.NewsItemDiffUtil
import id.go.jabarprov.dbmpr.utils.utils.CalendarUtils

class NewsAdapter : ListAdapter<News, NewsAdapter.NewsItemViewHolder>(NewsItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val binding =
            LayoutNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class NewsItemViewHolder(private val binding: LayoutNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.apply {
                imageViewNews.load(news.imageUrl) {
                    listener(
                        onSuccess = { _, _ ->
                            shimmerFrameLayoutLoadingNewsImage.stopShimmer()
                            shimmerFrameLayoutLoadingNewsImage.visibility = View.GONE
                            imageViewNews.isVisible = true
                        },
                    )
                }

                textViewTitle.text = news.title
                textViewSubtitle.text =
                    if (news.publishedAt != null) {
                        "${
                            CalendarUtils.formatCalendarToString(
                                news.publishedAt!!,
                                CalendarUtils.DATE_MONTH_YEAR_WITH_TIME_READABLE
                            )
                        } WIB"
                    } else {
                        "-"
                    }
                textViewBody.text = news.shortDescription
            }
        }
    }

}