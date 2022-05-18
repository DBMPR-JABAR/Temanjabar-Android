package id.go.jabarprov.dbmpr.feature.dashboard.presentation.diff_utils

import androidx.recyclerview.widget.DiffUtil
import id.go.jabarprov.dbmpr.common.domain.entity.News

class NewsItemDiffUtil : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.imageUrl == newItem.imageUrl && oldItem.content == newItem.content && oldItem.slug == newItem.slug
    }
}