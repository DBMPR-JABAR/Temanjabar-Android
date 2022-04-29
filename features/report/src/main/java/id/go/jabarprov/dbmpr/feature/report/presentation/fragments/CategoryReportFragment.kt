package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.common.widget.GridSpacingItemDecoration
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentCategoryReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.CategoryReportAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

private const val TAG = "CategoryReportFragment"

class CategoryReportFragment : Fragment() {

    private val categoryAdapter by lazy {
        CategoryReportAdapter(
            onDataEmpty = {
                binding.apply {
                    recyclerViewCategory.isVisible = false
                    layoutEmpty.root.isVisible = true
                }
            },
            onDataExist = {
                binding.apply {
                    recyclerViewCategory.isVisible = true
                    layoutEmpty.root.isVisible = false
                }
            }
        )
    }

    private lateinit var binding: FragmentCategoryReportBinding

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

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
        setUpRecyclerView()
        setUpSearchBoxCategoryReport()
    }

    private fun setUpSearchBoxCategoryReport() {
        binding.editTextSearchCategory.doOnTextChanged { text, _, _, _ ->
            job?.cancel()
            job = viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                delay(500)
                val filteredListCategoryReportModel = if (text.isNullOrEmpty()) {
                    LIST_CATEGORY_REPORT
                } else {
                    LIST_CATEGORY_REPORT.filter {
                        it.description.contains(text.toString(), ignoreCase = true)
                    }
                }
                categoryAdapter.submitList(filteredListCategoryReportModel)
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCategory.apply {
            adapter = categoryAdapter.apply {
                submitList(LIST_CATEGORY_REPORT)
            }
            layoutManager = object : GridLayoutManager(requireContext(), 4) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    lp?.width = width / spanCount
                    return true
                }
            }
            setHasFixedSize(true)
        }
    }

    companion object {
        private val LIST_CATEGORY_REPORT = listOf(
            CategoryReportModel(
                0,
                id.go.jabarprov.dbmpr.common.R.drawable.ic_damage_road,
                "Kerusakan Jalan"
            ),
            CategoryReportModel(
                1,
                id.go.jabarprov.dbmpr.common.R.drawable.ic_avalanche,
                "Bencana Alam"
            ),
            CategoryReportModel(
                2,
                id.go.jabarprov.dbmpr.common.R.drawable.ic_damage_road,
                "Kerusakan Jalan"
            ),
            CategoryReportModel(
                3,
                id.go.jabarprov.dbmpr.common.R.drawable.ic_avalanche,
                "Bencana Alam"
            ),
        )
    }

}