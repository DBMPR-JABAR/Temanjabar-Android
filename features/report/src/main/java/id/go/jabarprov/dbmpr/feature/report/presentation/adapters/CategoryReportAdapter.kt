package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.feature.report.databinding.LayoutCategoryReportItemBinding

class CategoryReportAdapter :
    RecyclerView.Adapter<CategoryReportAdapter.CategoryReportItemViewHolder>() {

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

    }

    override fun getItemCount(): Int {
        return 5
    }

    class CategoryReportItemViewHolder(private val binding: LayoutCategoryReportItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}