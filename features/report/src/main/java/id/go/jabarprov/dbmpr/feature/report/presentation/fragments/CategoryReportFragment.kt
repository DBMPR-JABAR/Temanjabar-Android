package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentCategoryReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.adapters.CategoryReportAdapter
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.MakeReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportAction
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "CategoryReportFragment"

@AndroidEntryPoint
class CategoryReportFragment : Fragment() {

    private val makeReportViewModel by viewModels<MakeReportViewModel>({
        requireParentFragment()
    })

    private val categoryAdapter by lazy {
        CategoryReportAdapter(
            onCategorySelected = {
                makeReportViewModel.processAction(MakeReportAction.UpdateSelectedCategory(it))
            },
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
        observeMakeReportState()
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
                makeReportViewModel.processAction(MakeReportAction.UpdateSearchKeyword(text.toString()))
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
            setHasFixedSize(true)
        }
    }

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                makeReportViewModel.uiState.collect {
                    categoryAdapter.submitList(it.currentListCategoryReport)
                }
            }
        }
    }

}