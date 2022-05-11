package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.common.databinding.LayoutThumbnailMediumBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils.PhotoModelDiffUtils
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

class PhotoAdapter(private val spanCount: Int, private val space: Int) :
    ListAdapter<PhotoModel, PhotoAdapter.PhotoItemViewHolder>(PhotoModelDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding =
            LayoutThumbnailMediumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.updateLayoutParams {
            width = (parent.measuredWidth - (space * (spanCount + 1))) / spanCount
            height = width
        }
        return PhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class PhotoItemViewHolder(private val binding: LayoutThumbnailMediumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photoModel: PhotoModel) {
            binding.apply {
                imageView.load(photoModel.uri)
            }
        }
    }

}