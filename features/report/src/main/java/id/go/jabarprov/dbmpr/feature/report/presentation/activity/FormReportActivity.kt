package id.go.jabarprov.dbmpr.feature.report.presentation.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.report.R
import id.go.jabarprov.dbmpr.feature.report.databinding.ActivityFormReportBinding
import id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report.CategoryReportFragment
import id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report.DetailReportFragment
import id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report.PhotoVideoReportFragment
import id.go.jabarprov.dbmpr.feature.report.presentation.fragments.form_report.PrivacyReportFragment
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.FormReportViewModel
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.FormReportAction
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportScreenState
import id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.report.store.MakeReportState
import id.go.jabarprov.dbmpr.utils.extensions.setEnabledRecursive
import kotlinx.coroutines.launch
import id.go.jabarprov.dbmpr.common.R as CommonR

@AndroidEntryPoint
class FormReportActivity : AppCompatActivity() {

    private val formReportViewModel by viewModels<FormReportViewModel>()

    private val binding by lazy { ActivityFormReportBinding.inflate(layoutInflater) }

    private val privacyReportFragment by lazy { PrivacyReportFragment() }
    private val categoryReportFragment by lazy { CategoryReportFragment() }
    private val photoVideoReportFragment by lazy { PhotoVideoReportFragment() }
    private val detailReportFragment by lazy { DetailReportFragment() }

    private var activeFragment: Fragment = privacyReportFragment

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
        if (supportFragmentManager.fragments.isEmpty()) initChildFragment()
        observeMakeReportState()
    }

    private fun initUI() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
            linearLayoutPrevious.setOnClickListener {
                formReportViewModel.processAction(FormReportAction.GoToPreviousScreen)
            }
            linearLayoutNext.setOnClickListener {
                formReportViewModel.processAction(FormReportAction.GoToNextScreen)
            }
        }
    }

    override fun onBackPressed() {
        if (formReportViewModel.uiState.value.screenState == MakeReportScreenState.PRIVACY) {
            super.onBackPressed()
        } else {
            formReportViewModel.processAction(FormReportAction.GoToPreviousScreen)
        }
    }

    private fun observeMakeReportState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                formReportViewModel.uiState.collect {
                    processScreenState(it.screenState)
                    processNextButtonState(it)
                }
            }
        }
    }

    private fun processScreenState(screenState: MakeReportScreenState) {
        when (screenState) {
            MakeReportScreenState.PRIVACY -> {
                setEnableReportPrivacyStep(false)
                setEnableReportCategoryStep(false)
                setEnableReportPhotoVideoStep(false)
                setEnableReportDetailStep(false)

                setSelectedReportPrivacyStep(true)
                setSelectedReportCategoryStep(false)
                setSelectedReportPhotoVideoStep(false)
                setSelectedReportDetailStep(false)

                navigateChildFragment(privacyReportFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = false
                }
            }
            MakeReportScreenState.CATEGORY -> {
                setEnableReportPrivacyStep(true)
                setEnableReportCategoryStep(false)
                setEnableReportPhotoVideoStep(false)
                setEnableReportDetailStep(false)

                setSelectedReportPrivacyStep(false)
                setSelectedReportCategoryStep(true)
                setSelectedReportPhotoVideoStep(false)
                setSelectedReportDetailStep(false)

                navigateChildFragment(categoryReportFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = true
                    textViewNext.text = getString(id.go.jabarprov.dbmpr.common.R.string.selanjutnya)
                }
            }
            MakeReportScreenState.PHOTO_VIDEO -> {
                setEnableReportPrivacyStep(true)
                setEnableReportCategoryStep(true)
                setEnableReportPhotoVideoStep(false)
                setEnableReportDetailStep(false)

                setSelectedReportPrivacyStep(false)
                setSelectedReportCategoryStep(false)
                setSelectedReportPhotoVideoStep(true)
                setSelectedReportDetailStep(false)

                navigateChildFragment(photoVideoReportFragment)
                binding.apply {
                    textViewNext.text = getString(id.go.jabarprov.dbmpr.common.R.string.selanjutnya)
                }
            }
            MakeReportScreenState.DETAIL -> {
                setEnableReportPrivacyStep(true)
                setEnableReportCategoryStep(true)
                setEnableReportPhotoVideoStep(true)
                setEnableReportDetailStep(false)

                setSelectedReportPrivacyStep(false)
                setSelectedReportCategoryStep(false)
                setSelectedReportPhotoVideoStep(false)
                setSelectedReportDetailStep(true)

                navigateChildFragment(detailReportFragment)
                binding.apply {
                    textViewNext.text = getString(id.go.jabarprov.dbmpr.common.R.string.selesai)
                }
            }
        }
    }

    private fun initChildFragment() {
        supportFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.frame_layout_fragment_container, detailReportFragment)
                hide(detailReportFragment)
                add(R.id.frame_layout_fragment_container, photoVideoReportFragment)
                hide(photoVideoReportFragment)
                add(R.id.frame_layout_fragment_container, categoryReportFragment)
                hide(categoryReportFragment)
                add(R.id.frame_layout_fragment_container, privacyReportFragment)
            }
            .commit()
    }

    private fun navigateChildFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                hide(activeFragment)
                show(fragment)
                activeFragment = fragment
            }
            .commit()
    }

    private fun setEnableReportPrivacyStep(value: Boolean) {
        binding.apply {
            if (value) {
                imageViewPrivacy.apply {
                    setImageResource(CommonR.drawable.ic_checklist)
                }
            } else {
                imageViewCategory.setImageResource(CommonR.drawable.ic_lock)
            }
            stepBar1.isEnabled = value
            imageViewPrivacy.isEnabled = value
            textViewStepPrivacy.isEnabled = value
        }
    }

    private fun setSelectedReportPrivacyStep(value: Boolean) {
        binding.apply {
            imageViewPrivacy.isSelected = value
            textViewStepPrivacy.isSelected = value
        }
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
            stepBar2.isEnabled = value
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
            stepBar3.isEnabled = value
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
            MakeReportScreenState.PRIVACY -> {
                binding.linearLayoutNext.setEnabledRecursive(true)
            }
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