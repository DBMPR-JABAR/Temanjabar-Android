package id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.LayoutPagerNewsBinding
import id.go.jabarprov.dbmpr.feature.dashboard.domain.entity.News

class NewsPagerAdapter(listNews: List<News>) :
    RecyclerView.Adapter<NewsPagerAdapter.NewsItemViewHolder>() {

    private var extendedList: List<News> =
        listOf(listNews.first()) + listNews + listOf(listNews.last())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutPagerNewsBinding.inflate(layoutInflater, parent, false)
        return NewsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(extendedList[position])
    }

    override fun getItemCount(): Int {
        return extendedList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newListNews: List<News>) {
        extendedList = listOf(newListNews.first()) + newListNews + listOf(newListNews.last())
        notifyDataSetChanged()
    }

    class NewsItemViewHolder(private val binding: LayoutPagerNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
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
            }
        }
    }

}