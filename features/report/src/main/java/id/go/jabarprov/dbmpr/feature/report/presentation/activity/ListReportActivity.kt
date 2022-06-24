package id.go.jabarprov.dbmpr.feature.report.presentation.activity

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.common.presentation.widgets.DividerVerticalItemDecoration
import id.go.jabarprov.dbmpr.feature.report.databinding.ActivityListReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.ReportGridAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.ReportListAdapter
import id.go.jabarprov.dbmpr.temanjabar.navigation.report.ReportNavigationModule
import javax.inject.Inject

@AndroidEntryPoint
class ListReportActivity : AppCompatActivity() {

    @Inject
    lateinit var reportNavigationModule: ReportNavigationModule

    private val binding by lazy {
        ActivityListReportBinding.inflate(layoutInflater)
    }

    private val reportGridAdapter by lazy {
        ReportGridAdapter(2, 32).setOnClickListener {
            reportNavigationModule.goToReport(this)
        }
    }

    private val reportListAdapter by lazy { ReportListAdapter() }

    private val gridItemDecoration by lazy {
        object : RecyclerView.ItemDecoration() {
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

    private val listItemDecoration by lazy {
        DividerVerticalItemDecoration(this)
    }

    private var listType = ListType.GRID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)
        windowInsetsController?.let {
            it.isAppearanceLightStatusBars = true
        }

        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }

            recyclerViewReport.apply {
                adapter = reportGridAdapter
                layoutManager = GridLayoutManager(this@ListReportActivity, 2)
                addItemDecoration(gridItemDecoration)
            }

            linearLayoutListType.setOnClickListener {
                if (listType == ListType.GRID) {
                    imageViewListType.setImageResource(id.go.jabarprov.dbmpr.common.R.drawable.ic_list)
                    textViewListType.text = "List"
                    listType = ListType.LIST
                    setListTypeList()
                } else {
                    imageViewListType.setImageResource(id.go.jabarprov.dbmpr.common.R.drawable.ic_grid)
                    textViewListType.text = "Grid"
                    listType = ListType.GRID
                    setListTypeGrid()
                }
            }

        }
    }

    private fun setListTypeGrid() {
        binding.recyclerViewReport.apply {
            adapter = reportGridAdapter
            layoutManager = GridLayoutManager(this@ListReportActivity, 2)
            removeItemDecoration(listItemDecoration)
            addItemDecoration(gridItemDecoration)
        }
    }

    private fun setListTypeList() {
        binding.recyclerViewReport.apply {
            adapter = reportListAdapter
            layoutManager = LinearLayoutManager(this@ListReportActivity)
            removeItemDecoration(gridItemDecoration)
            addItemDecoration(listItemDecoration)
        }
    }

    enum class ListType {
        GRID,
        LIST
    }

}