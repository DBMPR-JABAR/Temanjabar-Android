package id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.LayoutPagerNewsBinding
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.models.News

class NewsPagerAdapter(private val listNews: List<News>) :
    RecyclerView.Adapter<NewsPagerAdapter.NewsItemViewHolder>() {

    private val newListNews: List<News> =
        listOf(listNews.first()) + listNews + listOf(listNews.last())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutPagerNewsBinding.inflate(layoutInflater, parent, false)
        return NewsItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(newListNews[position])
    }

    override fun getItemCount(): Int {
        return newListNews.size
    }

    class NewsItemViewHolder(private val binding: LayoutPagerNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {

        }
    }

}