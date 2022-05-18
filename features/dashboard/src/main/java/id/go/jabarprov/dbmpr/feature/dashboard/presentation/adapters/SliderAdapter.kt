package id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.common.domain.entity.News
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.LayoutSliderItemBinding
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.diff_utils.NewsItemDiffUtil

class SliderAdapter :
    ListAdapter<News, SliderAdapter.NewsItemViewHolder>(NewsItemDiffUtil()) {

    private var onClickListener: ((News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutSliderItemBinding.inflate(layoutInflater, parent, false)
        return NewsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onClickListener)
        }
    }

    fun setOnClickListener(action: (News) -> Unit) = this.apply { onClickListener = action }

    class NewsItemViewHolder(private val binding: LayoutSliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News, action: ((News) -> Unit)?) {
            binding.apply {
                imageViewThumbnailNews.load(news.imageUrl) {
                    listener(
                        onSuccess = { _, _ ->
                            shimmerFrameLayoutLoadingNewsImage.stopShimmer()
                            shimmerFrameLayoutLoadingNewsImage.visibility = View.GONE
                            imageViewThumbnailNews.visibility = View.VISIBLE
                        },
                    )
                }
                root.setOnClickListener {
                    action?.invoke(news)
                }
            }
        }
    }

}