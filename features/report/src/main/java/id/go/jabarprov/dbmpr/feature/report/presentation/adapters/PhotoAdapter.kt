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

private const val TAG = "PhotoAdapter"

class PhotoAdapter :
    ListAdapter<PhotoModel, PhotoAdapter.PhotoItemViewHolder>(PhotoModelDiffUtils()) {

    private var hiddenItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding =
            LayoutThumbnailSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, hiddenItem)
        }
    }

    override fun submitList(list: List<PhotoModel>?) {
        hiddenItem = list?.size?.minus(5) ?: 0
        if (hiddenItem > 0) {
            notifyItemChanged(4)
        } else {
            super.submitList(list)
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size > 5) 5 else currentList.size
    }

    class PhotoItemViewHolder(private val binding: LayoutThumbnailSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoModel, hiddenItem: Int) {
            binding.apply {
                if (hiddenItem > 0) {
                    textViewMore.isVisible = true
                    textViewMore.text = "Lainnya\n$hiddenItem+"
                }
                imageView.load(photo.uri)
            }
        }
    }

}