package id.go.jabarprov.dbmpr.feature.authentication.presentation.fragments

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import id.go.jabarprov.dbmpr.feature.authentication.R
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentLoginBinding
import id.go.jabarprov.dbmpr.utils.extensions.getColorFromAttr
import kotlinx.coroutines.tasks.await

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private lateinit var binding: FragmentLoginBinding

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val googleSignInTask = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                requireContext().packageManager.getPackageInfo(context?.packageName!!, 0)
            textViewVersion.text = "App Version\n${packageInfo.versionName}"
        }
    }

    private fun setUpRegisterSpanText() {
        val registerAccountText = requireContext().getString(R.string.register_account)

        val startIndex = registerAccountText.indexOf("Daftar Sekarang")
        val endIndex = startIndex + "Daftar Sekarang".length

        val registerAccountSpanText = object : ClickableSpan() {
            override fun onClick(p0: View) {
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color =
                    requireContext().getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
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
                Log.d(TAG, "GOOGLE ACCOUNT: ${it.currentUser}")
            } else {
                Log.d(TAG, "GOOGLE ACCOUNT: USER SIGN OUT")
            }
        }
    }

    private fun launchSignInWithGoogleIntent() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val googleSignInIntent = googleSignInClient.signInIntent

        googleSignInLauncher.launch(googleSignInIntent)
    }

    private fun signInWithGoogle(idToken: String?) {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
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