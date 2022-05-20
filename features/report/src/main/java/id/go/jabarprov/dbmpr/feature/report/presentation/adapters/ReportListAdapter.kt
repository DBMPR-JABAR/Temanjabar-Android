package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.feature.report.databinding.LayoutItemReportListBinding

class ReportListAdapter : RecyclerView.Adapter<ReportListAdapter.ReportListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportListItemViewHolder {
        val binding =
            LayoutItemReportListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportListItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ReportListItemViewHolder(private val binding: LayoutItemReportListBinding) :
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