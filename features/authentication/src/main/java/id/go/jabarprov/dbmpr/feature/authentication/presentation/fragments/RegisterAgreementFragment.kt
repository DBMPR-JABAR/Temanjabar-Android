package id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentRegisterAgreementBinding

class RegisterAgreementFragment : Fragment() {

    private lateinit var binding: FragmentRegisterAgreementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAgreementBinding.inflate(inflater, container, false)
        return binding.root
    }

}