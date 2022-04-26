package id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.go.jabarprov.dbmpr.feature.authentication.R
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val registerNameFragment by lazy { RegisterNameFragment() }
    private val registerEmailFragment by lazy { RegisterEmailFragment() }
    private val registerPasswordFragment by lazy { RegisterPasswordFragment() }
    private val registerAgreementFragment by lazy { RegisterAgreementFragment() }

    private var indexPage = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setEnableStepFirst(false)
        setEnableStepSecond(false)
        setEnableStepThird(false)
        setEnableStepFourth(false)
        initChildFragment()
        binding.apply {
            linearLayoutNext.setOnClickListener {
                if (indexPage > 3) {
                    indexPage = 3
                } else {
                    indexPage += 1
                }
                when (indexPage) {
                    0 -> {
                        navigateChildFragment(registerNameFragment)
                    }
                    1 -> {
                        navigateChildFragment(registerEmailFragment)
                    }
                    2 -> {
                        navigateChildFragment(registerPasswordFragment)
                    }
                    3 -> {
                        navigateChildFragment(registerAgreementFragment)
                    }
                }
            }

            linearLayoutPrevious.setOnClickListener {
                if (indexPage > 0) {
                    indexPage -= 1
                } else {
                    indexPage = 0
                }
                when (indexPage) {
                    0 -> {
                        navigateChildFragment(registerNameFragment)
                    }
                    1 -> {
                        navigateChildFragment(registerEmailFragment)
                    }
                    2 -> {
                        navigateChildFragment(registerPasswordFragment)
                    }
                    3 -> {
                        navigateChildFragment(registerAgreementFragment)
                    }
                }
            }
        }
    }

    private fun initChildFragment() {
        childFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout_fragment_container, registerNameFragment)
            .commit()
    }

    private fun navigateChildFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_fragment_container, fragment)
            .commit()
    }

    private fun setEnableStepFirst(isEnable: Boolean) {
        binding.apply {
            textViewNo1.isEnabled = isEnable
            textViewStepNama.isEnabled = isEnable
            stepBar1.isEnabled = isEnable
        }
    }

    private fun setEnableStepSecond(isEnable: Boolean) {
        binding.apply {
            textViewNo2.isEnabled = isEnable
            textViewStepEmail.isEnabled = isEnable
            stepBar2.isEnabled = isEnable
        }
    }

    private fun setEnableStepThird(isEnable: Boolean) {
        binding.apply {
            textViewNo3.isEnabled = isEnable
            textViewStepPassword.isEnabled = isEnable
            stepBar3.isEnabled = isEnable
        }
    }

    private fun setEnableStepFourth(isEnable: Boolean) {
        binding.apply {
            textViewNo4.isEnabled = isEnable
            textViewStepAgreement.isEnabled = isEnable
        }
    }
}