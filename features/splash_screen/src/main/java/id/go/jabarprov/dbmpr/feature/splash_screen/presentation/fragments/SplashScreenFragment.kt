package id.go.jabarprov.dbmpr.feature.splash_screen.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import id.go.jabarprov.dbmpr.feature.splash_screen.R
import id.go.jabarprov.dbmpr.feature.splash_screen.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(1500)
            val action =
                NavDeepLinkRequest
                    .Builder
                    .fromUri("https://temanjabar.dbmpr.jabarprov.go.id/dashboard".toUri())
                    .build()
            findNavController().navigate(
                action,
                NavOptions.Builder().apply {
                    setPopUpTo(R.id.splashScreenFragment, true)
                }.build(),
            )
        }
    }
}