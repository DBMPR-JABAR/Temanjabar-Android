package id.go.jabarprov.dbmpr.feature.report.presentation.fragments.list_report

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentListReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.ReportGridAdapter
import id.go.jabarprov.dbmpr.common.R as CommonR

@AndroidEntryPoint
class ListReportFragment : Fragment() {

    private lateinit var binding: FragmentListReportBinding

    private val reportGridAdapter by lazy { ReportGridAdapter(2, 32) }

    private var listType = ListType.GRID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {

            recyclerViewReport.apply {
                adapter = reportGridAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                addItemDecoration(gridItemDecoration)
            }

            linearLayoutListType.setOnClickListener {
                if (listType == ListType.GRID) {
                    imageViewListType.setImageResource(CommonR.drawable.ic_list)
                    textViewListType.text = "List"
                    listType = ListType.LIST
                } else {
                    imageViewListType.setImageResource(CommonR.drawable.ic_grid)
                    textViewListType.text = "Grid"
                    listType = ListType.GRID
                }
            }

        }
    }

    private fun setListTypeGrid() {

    }

    private fun setListTypeList() {

    }

    companion object {
        private val gridItemDecoration = object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val position = parent.getChildAdapterPosition(view)

                if (position % 2 == 0) {
                    outRect.right = 16
                    outRect.left = 32
                } else {
                    outRect.left = 16
                    outRect.right = 32
                }

                if (position < 2) {
                    outRect.top = 32
                }

                outRect.bottom = 32
            }
        }
    }

    enum class ListType {
        GRID,
        LIST
    }
}