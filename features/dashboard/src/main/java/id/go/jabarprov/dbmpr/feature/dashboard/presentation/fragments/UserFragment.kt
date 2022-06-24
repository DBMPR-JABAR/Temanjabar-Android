package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentUserBinding
import id.go.jabarprov.dbmpr.temanjabar.navigation.dashboard.DashboardNavigationModule
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {

    @Inject
    lateinit var dashboardNavigationModule: DashboardNavigationModule

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
                dashboardNavigationModule.goToLoginScreen(requireContext())
            }

            val packageInfo =
                requireContext().packageManager.getPackageInfo(context?.packageName!!, 0)
            textViewVersion.text = "App Version\n${packageInfo.versionName}"
        }
    }
}