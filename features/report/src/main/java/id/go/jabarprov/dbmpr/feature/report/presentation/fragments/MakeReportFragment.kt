package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.common.presentation.widgets.ConfirmationDialog
import id.go.jabarprov.dbmpr.feature.report.R
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentMakeReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.MakeReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportScreenState
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportState
import id.go.jabarprov.dbmpr.utils.extensions.setEnabledRecursive
import kotlinx.coroutines.launch
import id.go.jabarprov.dbmpr.common.R as CommonR

@AndroidEntryPoint
class MakeReportFragment : Fragment() {

    private val makeReportViewModel by viewModels<MakeReportViewModel>()

    private lateinit var binding: FragmentMakeReportBinding

    private val categoryReportFragment by lazy { CategoryReportFragment() }
    private val photoVideoReportFragment by lazy { PhotoVideoReportFragment() }
    private val detailReportFragment by lazy { DetailReportFragment() }
    private val confirmationDialog by lazy {
        ConfirmationDialog.Builder()
            .setTitle("")
    }

    private var activeFragment: Fragment = categoryReportFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeMakeReportState()
        if (childFragmentManager.fragments.isEmpty()) initChildFragment()
    }

    private fun initUI() {
        binding.apply {
            linearLayoutPrevious.setOnClickListener {
                makeReportViewModel.processAction(MakeReportAction.GoToPreviousScreen)
            }
            linearLayoutNext.setOnClickListener {
                makeReportViewModel.processAction(MakeReportAction.GoToNextScreen)
            }
        }
    }

    private fun observeMakeReportState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                makeReportViewModel.uiState.collect {
                    processScreenState(it.screenState)
                    processNextButtonState(it)
                }
            }
        }
    }

    private fun processScreenState(screenState: MakeReportScreenState) {
        when (screenState) {
            MakeReportScreenState.CATEGORY -> {
                setEnableReportCategoryStep(false)
                setEnableReportPhotoVideoStep(false)
                setEnableReportDetailStep(false)

                setSelectedReportCategoryStep(true)
                setSelectedReportPhotoVideoStep(false)
                setSelectedReportDetailStep(false)

                navigateChildFragment(categoryReportFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = false
                }
            }
            MakeReportScreenState.PHOTO_VIDEO -> {
                setEnableReportCategoryStep(true)
                setEnableReportPhotoVideoStep(false)
                setEnableReportDetailStep(false)

                setSelectedReportCategoryStep(false)
                setSelectedReportPhotoVideoStep(true)
                setSelectedReportDetailStep(false)

                navigateChildFragment(photoVideoReportFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = true
                    textViewNext.text = getString(CommonR.string.selanjutnya)
                }
            }
            MakeReportScreenState.DETAIL -> {
                setEnableReportCategoryStep(true)
                setEnableReportPhotoVideoStep(true)
                setEnableReportDetailStep(false)

                setSelectedReportCategoryStep(false)
                setSelectedReportPhotoVideoStep(false)
                setSelectedReportDetailStep(true)

                navigateChildFragment(detailReportFragment)
                binding.apply {
                    textViewNext.text = getString(CommonR.string.selesai)
                }
            }
        }
    }

    private fun initChildFragment() {
        childFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.frame_layout_fragment_container, detailReportFragment)
                hide(detailReportFragment)
                add(R.id.frame_layout_fragment_container, photoVideoReportFragment)
                hide(photoVideoReportFragment)
                add(R.id.frame_layout_fragment_container, categoryReportFragment)
            }
            .commit()
    }

    private fun navigateChildFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .apply {
                hide(activeFragment)
                show(fragment)
                activeFragment = fragment
            }
            .commit()
    }

    private fun setEnableReportCategoryStep(value: Boolean) {
        binding.apply {
            if (value) {
                imageViewCategory.apply {
                    setImageResource(CommonR.drawable.ic_checklist)
                }
            } else {
                imageViewCategory.setImageResource(CommonR.drawable.ic_menu)
            }
            stepBar1.isEnabled = value
            imageViewCategory.isEnabled = value
            textViewStepCategory.isEnabled = value
        }
    }

    private fun setSelectedReportCategoryStep(value: Boolean) {
        binding.apply {
            imageViewCategory.isSelected = value
            textViewStepCategory.isSelected = value
        }
    }

    private fun setEnableReportPhotoVideoStep(value: Boolean) {
        binding.apply {
            if (value) {
                imageViewPhoto.setImageResource(CommonR.drawable.ic_checklist)
            } else {
                imageViewPhoto.setImageResource(CommonR.drawable.ic_camera)
            }
            stepBar2.isEnabled = value
            imageViewPhoto.isEnabled = value
            textViewStepPhoto.isEnabled = value
        }
    }

    private fun setSelectedReportPhotoVideoStep(value: Boolean) {
        binding.apply {
            imageViewPhoto.isSelected = value
            textViewStepPhoto.isSelected = value
        }
    }

    private fun setEnableReportDetailStep(value: Boolean) {
        binding.apply {
            if (value) {
                imageViewDetail.setImageResource(CommonR.drawable.ic_checklist)
            } else {
                imageViewDetail.setImageResource(CommonR.drawable.ic_document)
            }
            imageViewDetail.isEnabled = value
            textViewStepDetail.isEnabled = value
        }
    }

    private fun setSelectedReportDetailStep(value: Boolean) {
        binding.apply {
            imageViewDetail.isSelected = value
            textViewStepDetail.isSelected = value
        }
    }

    private fun processNextButtonState(state: MakeReportState) {
        when (state.screenState) {
            MakeReportScreenState.CATEGORY -> {
                if (state.selectedCategory == null) {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                }
            }
            MakeReportScreenState.PHOTO_VIDEO -> {
                if (state.currentListPhoto.isEmpty()) {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                }
            }
            MakeReportScreenState.DETAIL -> {
                if (state.description.isNullOrBlank() || state.location.isNullOrBlank()) {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                }
            }
        }
    }

}