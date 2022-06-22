package id.go.jabarprov.dbmpr.feature.dashboard.presentation.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.dashboard.R
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.ActivityDashboardBinding
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments.FaqFragment
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments.HomeFragment
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments.MessageFragment
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.fragments.UserFragment

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }

    private val homeFragment by lazy { HomeFragment() }
    private val messageFragment by lazy { MessageFragment() }
    private val faqFragment by lazy { FaqFragment() }
    private val userFragment by lazy { UserFragment() }

    private var activeFragment: Fragment = homeFragment

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
    }

    private fun initUI() {
        binding.apply {
            buttonReport.setOnClickListener {
//                val action =
//                    NavDeepLinkRequest.Builder.fromUri("https://temanjabar.dbmpr.jabarprov.go.id/report".toUri())
//                        .build()
//                findNavController().navigate(action)
            }

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
                    R.id.dashboard_menu_account -> {
                        navigateChildFragment(userFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initChildFragment() {
        supportFragmentManager
            .beginTransaction()
            .apply {
                add(R.id.frame_layout_fragment_container, userFragment)
                hide(userFragment)
                add(R.id.frame_layout_fragment_container, faqFragment)
                hide(faqFragment)
                add(R.id.frame_layout_fragment_container, messageFragment)
                hide(messageFragment)
                add(R.id.frame_layout_fragment_container, homeFragment)
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
}