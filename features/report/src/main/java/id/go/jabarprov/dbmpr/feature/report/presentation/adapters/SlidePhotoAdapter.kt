package id.go.jabarprov.dbmpr.feature.report.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.go.jabarprov.dbmpr.feature.report.databinding.LayoutSlidePhotoItemBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.models.ReportModel

class SlidePhotoAdapter(private val listReport: List<ReportModel>) :
    RecyclerView.Adapter<SlidePhotoAdapter.SlidePhotoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlidePhotoItemViewHolder {
        val binding =
            LayoutSlidePhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlidePhotoItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SlidePhotoItemViewHolder,
        position: Int
    ) {
        if (position == 0) {
            holder.showVideo(listReport[position])
        } else {
            holder.showImage(listReport[position])
        }
    }

    override fun getItemCount(): Int {
        return listReport.size
    }

    class SlidePhotoItemViewHolder(private val binding: LayoutSlidePhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun showImage(report: ReportModel) {
            binding.apply {
                imageView.load(report.imageUrl) {
                    listener(onSuccess = { _, _ ->
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        imageView.visibility = View.VISIBLE
                    })
                }
            }
        }

        fun showVideo(report: ReportModel) {
            binding.apply {
                imageView.load(report.imageUrl) {
                    listener(onSuccess = { _, _ ->
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        imageView.visibility = View.VISIBLE
                        imageViewIconPlay.visibility = View.VISIBLE
                    })
                }
            }
        }

    }

}