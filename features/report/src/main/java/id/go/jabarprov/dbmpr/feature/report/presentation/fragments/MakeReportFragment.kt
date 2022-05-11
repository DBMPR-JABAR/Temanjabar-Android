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
import id.go.jabarprov.dbmpr.feature.report.R
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentMakeReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.MakeReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportScreenState
import kotlinx.coroutines.launch
import id.go.jabarprov.dbmpr.common.R as CommonR

@AndroidEntryPoint
class MakeReportFragment : Fragment() {

    private val makeReportViewModel by viewModels<MakeReportViewModel>()

    private lateinit var binding: FragmentMakeReportBinding

    private val categoryReportFragment by lazy { CategoryReportFragment() }
    private val photoVideoReportFragment by lazy { PhotoVideoReportFragment() }
    private val detailReportFragment by lazy { DetailReportFragment() }

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

    private fun navigateChildFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_fragment_container, fragment)
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

}