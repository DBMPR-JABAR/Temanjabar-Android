package id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils

import androidx.recyclerview.widget.DiffUtil
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

class PhotoModelDiffUtils : DiffUtil.ItemCallback<PhotoModel>() {
    override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem.uri == newItem.uri && oldItem.isSelected == newItem.isSelected
    }
}