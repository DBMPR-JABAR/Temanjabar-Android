package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.common.widget.GridSpacingItemDecoration
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentCategoryReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.CategoryReportAdapter

class CategoryReportFragment : Fragment() {

    private val categoryAdapter by lazy { CategoryReportAdapter() }

    private lateinit var binding: FragmentCategoryReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.recyclerViewCategory.apply {
            adapter = categoryAdapter
            layoutManager = object : GridLayoutManager(requireContext(), 4) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    lp?.width = width / spanCount
                    return true
                }
            }
        }
    }

}