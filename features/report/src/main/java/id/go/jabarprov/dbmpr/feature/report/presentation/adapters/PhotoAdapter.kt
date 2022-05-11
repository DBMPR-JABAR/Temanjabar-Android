package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.common.databinding.LayoutThumbnailMediumBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils.PhotoModelDiffUtils
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import id.go.jabarprov.dbmpr.utils.extensions.setSelectedRecursive

class PhotoAdapter(private val spanCount: Int, private val space: Int) :
    ListAdapter<PhotoModel, PhotoAdapter.PhotoItemViewHolder>(PhotoModelDiffUtils()) {

    private var onClickListener: ((PhotoModel) -> Unit)? = null

    private var onLongClickListener: ((PhotoModel) -> Unit)? = null

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
            holder.bind(it, onClickListener, onLongClickListener)
        }
    }

    fun setOnClickListener(action: (PhotoModel) -> Unit) {
        onClickListener = action
    }

    fun setOnLongClickListener(action: (PhotoModel) -> Unit) {
        onLongClickListener = action
    }

    class PhotoItemViewHolder(private val binding: LayoutThumbnailMediumBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            photoModel: PhotoModel,
            onClick: ((PhotoModel) -> Unit)?,
            onLongClick: ((PhotoModel) -> Unit)?
        ) {
            binding.apply {
                root.apply {
                    setSelectedRecursive(photoModel.isSelected)
                    imageViewChecked.isVisible = photoModel.isSelected

                    setOnClickListener {
                        onClick?.invoke(photoModel)
                    }

                    setOnLongClickListener {
                        onLongClick?.invoke(photoModel)
                        true
                    }
                }
                imageView.load(photoModel.uri)
            }
        }
    }

}