package id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentRegisterAgreementBinding
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.RegisterViewModel
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterAction

@AndroidEntryPoint
class RegisterAgreementFragment : Fragment() {

    private val registerViewModel by viewModels<RegisterViewModel>({ requireParentFragment() })

    private lateinit var binding: FragmentRegisterAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAgreementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            checkboxAgreement.setOnCheckedChangeListener { _, isAgree ->
                registerViewModel.processAction(RegisterAction.UpdateAgreement(isAgree))
            }
        }
    }

}