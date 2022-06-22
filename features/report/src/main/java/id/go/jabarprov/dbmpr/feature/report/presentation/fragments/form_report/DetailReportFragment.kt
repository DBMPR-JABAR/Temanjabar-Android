package id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentDetailReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.MakeReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportAction
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailReportFragment : Fragment() {

    private val makeReportViewModel by activityViewModels<MakeReportViewModel>()

    private lateinit var binding: FragmentDetailReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeMakeReportState()
    }

    private fun initUI() {
        binding.apply {
            editTextDescription.doOnTextChanged { text, _, _, _ ->
                makeReportViewModel.processAction(MakeReportAction.UpdateDescription(text.toString()))
            }

            editTextLocation.doOnTextChanged { text, _, _, _ ->
                makeReportViewModel.processAction(MakeReportAction.UpdateLocation(text.toString()))
            }

            editTextExplanation.doOnTextChanged { text, _, _, _ ->
                makeReportViewModel.processAction(MakeReportAction.UpdateExplanation(text.toString()))
            }
        }
    }

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                makeReportViewModel.uiState.collect {
                    binding.apply {
                        textInputLayoutDescription.error = it.descriptionErrorMessage
                        textInputLayoutLocation.error = it.locationErrorMessage
                    }
                }
            }
        }
    }
}