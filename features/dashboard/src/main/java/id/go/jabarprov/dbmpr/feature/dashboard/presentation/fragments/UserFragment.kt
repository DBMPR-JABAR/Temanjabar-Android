package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        binding.apply {
            buttonLogin.setOnClickListener {
                val request = NavDeepLinkRequest
                    .Builder
                    .fromUri("https://temanjabar.dbmpr.jabarprov.go.id/webview?url=https://tj.temanjabar.net/login".toUri())
                    .build()
                findNavController().navigate(request)
            }

            val packageInfo =
                requireContext().packageManager.getPackageInfo(context?.packageName!!, 0)
            textViewVersion.text = "App Version\n${packageInfo.versionName}"
        }
    }
}