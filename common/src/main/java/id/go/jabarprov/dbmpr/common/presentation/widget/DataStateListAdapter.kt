package id.go.jabarprov.dbmpr.common.presentation.widget

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DataStateListAdapter<T, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<T>,
    private val onDataEmpty: () -> Unit,
    private val onDataExist: () -> Unit
) : ListAdapter<T, VH>(diffUtil) {
    override fun submitList(list: List<T>?) {
        if (list.isNullOrEmpty()) {
            onDataEmpty()
        } else {
            onDataExist()
        }
        super.submitList(list)
    }
}