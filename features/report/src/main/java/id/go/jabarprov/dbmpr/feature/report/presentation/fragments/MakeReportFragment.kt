package id.go.jabarprov.dbmpr.feature.report.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.R
import id.go.jabarprov.dbmpr.feature.report.databinding.FragmentMakeReportBinding
import id.go.jabarprov.dbmpr.common.R as CommonR

@AndroidEntryPoint
class MakeReportFragment : Fragment() {

    private lateinit var binding: FragmentMakeReportBinding

    private val categoryReportFragment by lazy { CategoryReportFragment() }

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
    }

    private fun initUI() {
        setEnableReportCategoryStep(false)
        setEnableReportPhotoVideoStep(false)
        setEnableReportDetailStep(false)

        binding.apply {
            imageViewCategory.isSelected = true
            textViewStepCategory.isSelected = true
        }

        childFragmentManager.beginTransaction()
            .add(R.id.frame_layout_fragment_container, categoryReportFragment)
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

}