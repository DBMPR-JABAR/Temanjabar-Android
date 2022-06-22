package id.go.jabarprov.dbmpr.feature.splash_screen.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.splash_screen.databinding.ActivitySplashScreenBinding
import id.go.jabarprov.dbmpr.feature.splash_screen.navigation.SplashScreenDirections
import id.go.jabarprov.dbmpr.temanjabar.navigation.splash_screen.SplashScreenNavigationModule
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }

    @Inject
    lateinit var splashScreenNavigationModule: SplashScreenNavigationModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            delay(2000)
            splashScreenNavigationModule.goToDashboardScreen(this@SplashScreenActivity)
        }
    }
}