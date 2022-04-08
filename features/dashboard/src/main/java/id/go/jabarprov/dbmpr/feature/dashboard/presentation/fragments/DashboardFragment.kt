package id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.go.jabarprov.dbmpr.feature.dashboard.R
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val homeFragment by lazy { HomeFragment() }
    private val messageFragment by lazy { MessageFragment() }
    private val faqFragment by lazy { FaqFragment() }
    private val userFragment by lazy { UserFragment() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initChildFragment()
    }

    private fun initUI() {
        binding.apply {
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.dashboard_menu_home -> {
                        navigateChildFragment(homeFragment)
                        true
                    }
                    R.id.dashboard_menu_message -> {
                        navigateChildFragment(messageFragment)
                        true
                    }
                    R.id.dashboard_menu_faq -> {
                        navigateChildFragment(faqFragment)
                        true
                    }
                    R.id.dashboard_menu_user -> {
                        navigateChildFragment(userFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initChildFragment() {
        childFragmentManager
            .beginTransaction()
            .add(R.id.frame_layout_fragment_container, homeFragment)
            .commit()
    }

    private fun navigateChildFragment(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout_fragment_container, fragment)
            .commit()
    }
}