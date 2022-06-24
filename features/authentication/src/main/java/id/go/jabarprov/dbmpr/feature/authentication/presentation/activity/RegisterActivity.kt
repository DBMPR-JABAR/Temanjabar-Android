package id.go.jabarprov.dbmpr.feature.authentication.presentation.activity

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
import id.go.jabarprov.dbmpr.feature.authentication.R
import id.go.jabarprov.dbmpr.feature.authentication.databinding.ActivityRegisterBinding
import id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments.RegisterAgreementFragment
import id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments.RegisterEmailFragment
import id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments.RegisterNameFragment
import id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments.RegisterPasswordFragment
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.RegisterViewModel
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterAction
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterScreenState
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterState
import id.go.jabarprov.dbmpr.utils.extensions.setEnabledRecursive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by viewModels<RegisterViewModel>()

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    private val registerNameFragment by lazy { RegisterNameFragment() }
    private val registerEmailFragment by lazy { RegisterEmailFragment() }
    private val registerPasswordFragment by lazy { RegisterPasswordFragment() }
    private val registerAgreementFragment by lazy { RegisterAgreementFragment() }

    private var activeFragment: Fragment = registerNameFragment

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
        observeRegisterState()
    }

    private fun initUI() {
        binding.apply {
            linearLayoutNext.setOnClickListener {
                registerViewModel.processAction(RegisterAction.GoToNextScreen)
            }

            linearLayoutPrevious.setOnClickListener {
                registerViewModel.processAction(RegisterAction.GoToPreviousScreen)
            }

            buttonBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (registerViewModel.uiState.value.screenState == RegisterScreenState.REGISTER_NAME) {
            super.onBackPressed()
        } else {
            registerViewModel.processAction(RegisterAction.GoToPreviousScreen)
        }
    }

    private fun initChildFragment() {
        supportFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.frame_layout_fragment_container, registerAgreementFragment)
                hide(registerAgreementFragment)
                add(R.id.frame_layout_fragment_container, registerPasswordFragment)
                hide(registerPasswordFragment)
                add(R.id.frame_layout_fragment_container, registerEmailFragment)
                hide(registerEmailFragment)
                add(R.id.frame_layout_fragment_container, registerNameFragment)
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

    private fun setEnableFirstStep(isEnable: Boolean) {
        binding.apply {
            textViewStepNama.isEnabled = isEnable
            stepBar1.isEnabled = isEnable

            if (!isEnable) {
                textViewNo1.isVisible = true
                imageViewChecklistStep1.isVisible = false
            } else {
                textViewNo1.isVisible = false
                imageViewChecklistStep1.isVisible = true
            }
        }
    }

    private fun setSelectedFirstStep(value: Boolean) {
        binding.apply {
            textViewNo1.isSelected = value
            textViewStepNama.isSelected = value
        }
    }

    private fun setEnableSecondStep(isEnable: Boolean) {
        binding.apply {
            textViewStepEmail.isEnabled = isEnable
            stepBar2.isEnabled = isEnable

            if (!isEnable) {
                textViewNo2.isVisible = true
                imageViewChecklistStep2.isVisible = false
            } else {
                textViewNo2.isVisible = false
                imageViewChecklistStep2.isVisible = true
            }
        }
    }

    private fun setSelectedSecondStep(value: Boolean) {
        binding.apply {
            textViewNo2.isSelected = value
            textViewStepEmail.isSelected = value
        }
    }

    private fun setEnableThridStep(isEnable: Boolean) {
        binding.apply {
            textViewStepPassword.isEnabled = isEnable
            stepBar3.isEnabled = isEnable

            if (!isEnable) {
                textViewNo3.isVisible = true
                imageViewChecklistStep3.isVisible = false
            } else {
                textViewNo3.isVisible = false
                imageViewChecklistStep3.isVisible = true
            }
        }
    }

    private fun setSelectedThirdStep(value: Boolean) {
        binding.apply {
            textViewNo3.isSelected = value
            textViewStepPassword.isSelected = value
        }
    }

    private fun setEnableFourthStep(isEnable: Boolean) {
        binding.apply {
            textViewStepAgreement.isEnabled = isEnable

            if (!isEnable) {
                textViewNo4.isVisible = true
                imageViewChecklistStep4.isVisible = false
            } else {
                textViewNo4.isVisible = false
                imageViewChecklistStep4.isVisible = true
            }
        }
    }

    private fun setSelectedFourthStep(value: Boolean) {
        binding.apply {
            textViewNo4.isSelected = value
            textViewStepAgreement.isSelected = value
        }
    }

    private fun observeRegisterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                registerViewModel.uiState.collect {
                    processScreenState(it.screenState)
                    processNextButtonState(it)
                }
            }
        }
    }

    private fun processScreenState(state: RegisterScreenState) {
        when (state) {
            RegisterScreenState.REGISTER_NAME -> {
                setEnableFirstStep(false)
                setEnableSecondStep(false)
                setEnableThridStep(false)
                setEnableFourthStep(false)

                setSelectedFirstStep(true)
                setSelectedSecondStep(false)
                setSelectedThirdStep(false)
                setSelectedFourthStep(false)

                navigateChildFragment(registerNameFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = false
                }
            }
            RegisterScreenState.REGISTER_EMAIL -> {
                setEnableFirstStep(true)
                setEnableSecondStep(false)
                setEnableThridStep(false)
                setEnableFourthStep(false)

                setSelectedFirstStep(false)
                setSelectedSecondStep(true)
                setSelectedThirdStep(false)
                setSelectedFourthStep(false)

                navigateChildFragment(registerEmailFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = true
                }
            }
            RegisterScreenState.REGISTER_PASSWORD -> {
                setEnableFirstStep(true)
                setEnableSecondStep(true)
                setEnableThridStep(false)
                setEnableFourthStep(false)

                setSelectedFirstStep(false)
                setSelectedSecondStep(false)
                setSelectedThirdStep(true)
                setSelectedFourthStep(false)

                navigateChildFragment(registerPasswordFragment)
                binding.apply {
                    linearLayoutPrevious.isVisible = true
                    textViewNext.text = getString(id.go.jabarprov.dbmpr.common.R.string.selanjutnya)
                }
            }
            RegisterScreenState.REGISTER_AGREEMENT -> {
                setEnableFirstStep(true)
                setEnableSecondStep(true)
                setEnableThridStep(true)
                setEnableFourthStep(false)

                setSelectedFirstStep(false)
                setSelectedSecondStep(false)
                setSelectedThirdStep(false)
                setSelectedFourthStep(true)

                navigateChildFragment(
                    registerAgreementFragment
                )
                binding.apply {
                    linearLayoutPrevious.isVisible = true
                    textViewNext.text = getString(id.go.jabarprov.dbmpr.common.R.string.selesai)
                }
            }
        }
    }

    private fun processNextButtonState(state: RegisterState) {
        when (state.screenState) {
            RegisterScreenState.REGISTER_NAME -> {
                if (!state.name.isNullOrBlank() && state.nameError == null) {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                }
            }
            RegisterScreenState.REGISTER_EMAIL -> {
                if (!state.email.isNullOrBlank() && state.emailError == null) {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                }
            }
            RegisterScreenState.REGISTER_PASSWORD -> {
                if (!state.password.isNullOrBlank() && !state.repeatPassword.isNullOrBlank() && state.passwordError == null && state.repeatPasswordError == null) {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                }
            }
            RegisterScreenState.REGISTER_AGREEMENT -> {
                if (state.isAgree) {
                    binding.linearLayoutNext.setEnabledRecursive(true)
                } else {
                    binding.linearLayoutNext.setEnabledRecursive(false)
                }
            }
        }
    }
}