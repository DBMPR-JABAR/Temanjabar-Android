package id.go.jabarprov.dbmpr.feature.authentication.presentation.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.authentication.R
import id.go.jabarprov.dbmpr.feature.authentication.databinding.ActivityLoginBinding
import id.go.jabarprov.dbmpr.temanjabar.navigation.auth.AuthNavigationModule
import id.go.jabarprov.dbmpr.utils.extensions.getColorFromAttr
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var authNavigationModule: AuthNavigationModule

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val googleSignInTask = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            lifecycleScope.launchWhenResumed {
                try {
                    val googleAccount = googleSignInTask.await()
                    if (googleAccount.idToken != null) {
                        signInWithGoogle(googleAccount.idToken)
                    } else {
                        Log.d(TAG, "GOOGLE ACCOUNT: ${googleAccount.displayName}")
                    }
                } catch (e: ApiException) {
                    Log.e(TAG, "SIGN IN WITH GOOGLE: FAILED", e)
                }
            }
        }

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

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
        setUpGoogleAccountStateListener()
    }

    private fun initUI() {
        setUpRegisterSpanText()
        binding.apply {
            buttonLoginWithGoogle.setOnClickListener {
                launchSignInWithGoogleIntent()
            }

            val packageInfo =
                packageManager.getPackageInfo(packageName!!, 0)
            textViewVersion.text = "App Version\n${packageInfo.versionName}"
        }
    }

    private fun setUpRegisterSpanText() {
        val registerAccountText = getString(R.string.register_account)

        val startIndex = registerAccountText.indexOf("Daftar Sekarang")
        val endIndex = startIndex + "Daftar Sekarang".length

        val registerAccountSpanText = object : ClickableSpan() {
            override fun onClick(p0: View) {
                authNavigationModule.goToRegisterScreen(this@LoginActivity)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
            }
        }

        val spannableString = SpannableString(registerAccountText).apply {
            setSpan(
                registerAccountSpanText,
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.textViewRegister.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setUpGoogleAccountStateListener() {
        FirebaseAuth.AuthStateListener {
            if (it.currentUser != null) {
                Log.d(
                    TAG,
                    "GOOGLE ACCOUNT: ${it.currentUser}"
                )
            } else {
                Log.d(
                    TAG,
                    "GOOGLE ACCOUNT: USER SIGN OUT"
                )
            }
        }
    }

    private fun launchSignInWithGoogleIntent() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val googleSignInIntent = googleSignInClient.signInIntent

        googleSignInLauncher.launch(googleSignInIntent)
    }

    private fun signInWithGoogle(idToken: String?) {
        lifecycleScope.launchWhenResumed {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val user = firebaseAuth.signInWithCredential(credential).await().user
                Log.d(TAG, "SIGN IN WITH GOOGLE: SUCCESS ${user?.displayName}")
            } catch (e: FirebaseAuthException) {
                Log.e(TAG, "SIGN IN WITH GOOGLE: FAILED", e)
            }
        }
    }
}