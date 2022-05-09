package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.common.databinding.LayoutThumbnailSmallBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils.PhotoModelDiffUtils
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

class PhotoAdapter :
    ListAdapter<PhotoModel, PhotoAdapter.PhotoItemViewHolder>(PhotoModelDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding =
            LayoutThumbnailSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size > 5) 5 else currentList.size
    }

    class PhotoItemViewHolder(private val binding: LayoutThumbnailSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoModel, position: Int) {
            binding.apply {
                if (position >= 5) {
                    textViewMore.isVisible = true
                }
                imageView.load(photo.uri)
            }
        }
    }

}