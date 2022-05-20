package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.feature.report.databinding.LayoutItemReportGridBinding


class ReportGridAdapter(private val spanCount: Int, private val space: Int) :
    RecyclerView.Adapter<ReportGridAdapter.ReportGridItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportGridItemViewHolder {
        val binding =
            LayoutItemReportGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.updateLayoutParams {
            width = (parent.measuredWidth - (space * (spanCount + 1))) / spanCount
        }
        return ReportGridItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportGridItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ReportGridItemViewHolder(private val binding: LayoutItemReportGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                imageView.load("https://thumbs.dreamstime.com/b/dangerous-pothole-asphalt-rural-road-damage-149107515.jpg") {
                    listener(
                        onSuccess = { _, _ ->
                            shimmerFrameLayout.stopShimmer()
                            shimmerFrameLayout.visibility = View.GONE
                            imageView.visibility = View.VISIBLE
                        },
                    )
                }
            }
        }
    }

}