package id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentRegisterEmailBinding
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.RegisterViewModel
import id.go.jabarprov.dbmpr.feature.authentication.presentation.viewmodels.register.store.RegisterAction
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterEmailFragment : Fragment() {

    private val registerViewModel by viewModels<RegisterViewModel>({
        requireParentFragment()
    })

    private lateinit var binding: FragmentRegisterEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeRegisterState()
    }

    private fun initUI() {
        binding.apply {
            editTextEmail.doOnTextChanged { text, _, _, _ ->
                registerViewModel.processAction(RegisterAction.UpdateEmail(text.toString()))
            }
        }
    }

    private fun observeRegisterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                registerViewModel.uiState.collect {
                    binding.textInputLayoutEmail.error = it.emailError
                }
            }
        }
    }
}