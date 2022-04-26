package id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

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