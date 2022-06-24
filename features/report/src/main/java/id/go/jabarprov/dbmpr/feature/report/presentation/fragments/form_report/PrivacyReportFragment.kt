package id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentPrivacyReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.FormReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.FormReportAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportPrivacy
import id.go.jabarprov.dbmpr.utils.extensions.setSelectedRecursive
import kotlinx.coroutines.launch
import id.go.jabarprov.dbmpr.core_views.R as CoreR

@AndroidEntryPoint
class PrivacyReportFragment : Fragment() {

    private val formReportViewModel by activityViewModels<FormReportViewModel>()

    private lateinit var binding: FragmentPrivacyReportBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrivacyReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeMakeReportState()
    }

    private fun initUI() {
        binding.apply {
            materialCardViewPrivate.setOnClickListener {
                formReportViewModel.processAction(
                    FormReportAction.UpdateReportPrivacy(
                        MakeReportPrivacy.PRIVATE
                    )
                )
            }

            materialCardViewPublic.setOnClickListener {
                formReportViewModel.processAction(
                    FormReportAction.UpdateReportPrivacy(
                        MakeReportPrivacy.PUBLIC
                    )
                )
            }
        }
    }

    private fun setSelectedPrivate(value: Boolean) {
        binding.apply {
            if (value) {
                constraintLayoutPrivate.setSelectedRecursive(true)
                val typedValue = TypedValue()
                requireContext().theme.resolveAttribute(
                    com.google.android.material.R.attr.colorPrimaryVariant,
                    typedValue,
                    true
                )
                textViewDescPrivate.setTextColor(typedValue.data)
                imageViewPrivateChecked.visibility = View.VISIBLE
            } else {
                constraintLayoutPrivate.setSelectedRecursive(false)
                val typedValue = TypedValue()
                requireContext().theme.resolveAttribute(
                    CoreR.attr.bodyTextColor,
                    typedValue,
                    true
                )
                textViewDescPrivate.setTextColor(typedValue.data)
                imageViewPrivateChecked.visibility = View.INVISIBLE
            }
        }
    }

    private fun setSelectedPublic(value: Boolean) {
        binding.apply {
            if (value) {
                constraintLayoutPublic.setSelectedRecursive(true)
                val typedValue = TypedValue()
                requireContext().theme.resolveAttribute(
                    com.google.android.material.R.attr.colorPrimaryVariant,
                    typedValue,
                    true
                )
                textViewDescPublic.setTextColor(typedValue.data)
                imageViewPublicChecked.visibility = View.VISIBLE
            } else {
                constraintLayoutPublic.setSelectedRecursive(false)
                val typedValue = TypedValue()
                requireContext().theme.resolveAttribute(
                    CoreR.attr.bodyTextColor,
                    typedValue,
                    true
                )
                textViewDescPublic.setTextColor(typedValue.data)
                imageViewPublicChecked.visibility = View.INVISIBLE
            }
        }
    }

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                formReportViewModel.uiState.collect {
                    processReportPrivacy(it.reportPrivacy)
                }
            }
        }
    }

    private fun processReportPrivacy(privacy: MakeReportPrivacy) {
        when (privacy) {
            MakeReportPrivacy.PRIVATE -> {
                setSelectedPrivate(true)
                setSelectedPublic(false)
            }
            MakeReportPrivacy.PUBLIC -> {
                setSelectedPrivate(false)
                setSelectedPublic(true)
            }
        }
    }

}