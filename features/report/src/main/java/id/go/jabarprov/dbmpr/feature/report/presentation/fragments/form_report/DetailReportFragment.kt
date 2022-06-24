package id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentDetailReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.FormReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.FormReportAction
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailReportFragment : Fragment() {

    private val formReportViewModel by activityViewModels<FormReportViewModel>()

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
                formReportViewModel.processAction(FormReportAction.UpdateDescription(text.toString()))
            }

            editTextLocation.doOnTextChanged { text, _, _, _ ->
                formReportViewModel.processAction(FormReportAction.UpdateLocation(text.toString()))
            }

            editTextExplanation.doOnTextChanged { text, _, _, _ ->
                formReportViewModel.processAction(FormReportAction.UpdateExplanation(text.toString()))
            }
        }
    }

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                formReportViewModel.uiState.collect {
                    binding.apply {
                        textInputLayoutDescription.error = it.descriptionErrorMessage
                        textInputLayoutLocation.error = it.locationErrorMessage
                    }
                }
            }
        }
    }
}