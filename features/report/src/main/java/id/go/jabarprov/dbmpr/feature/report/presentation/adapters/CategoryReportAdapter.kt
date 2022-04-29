package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.common.widget.DataStateListAdapter
import id.go.jabarprov.dbmpr.feature.report.databinding.LayoutCategoryReportItemBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.diff_utils.CategoryReportModelDiffUtils
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import id.go.jabarprov.dbmpr.utils.extensions.setSelectedRecursive

class CategoryReportAdapter(onDataEmpty: () -> Unit, onDataExist: () -> Unit) :
    DataStateListAdapter<CategoryReportModel, CategoryReportAdapter.CategoryReportItemViewHolder>(
        CategoryReportModelDiffUtils(),
        onDataEmpty,
        onDataExist
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryReportItemViewHolder {
        val binding = LayoutCategoryReportItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryReportItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryReportItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class CategoryReportItemViewHolder(private val binding: LayoutCategoryReportItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryReportModel: CategoryReportModel) {
            binding.apply {
                if (categoryReportModel.isSelected) {
                    root.setSelectedRecursive(true)
                } else {
                    root.setSelectedRecursive(false)
                }
                imageViewIconCategory.setImageResource(categoryReportModel.icon)
                textViewCategory.text = categoryReportModel.description
            }
        }
    }

}