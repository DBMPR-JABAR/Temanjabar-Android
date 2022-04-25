package id.go.jabarprov.dbmpr.feature.authentication.presentation

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.go.jabarprov.dbmpr.feature.authentication.R
import id.go.jabarprov.dbmpr.feature.authentication.databinding.FragmentLoginBinding
import id.go.jabarprov.dbmpr.utils.extensions.getColorFromAttr
import id.go.jabarprov.dbmpr.utils.extensions.showToast

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

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
    }

    private fun initUI() {
        setUpAgreementSpanText()
    }

    private fun setUpAgreementSpanText() {
        val agreementText = requireContext().getString(R.string.agreement)

        val termAndConditionStartIndex = agreementText.indexOf("Terms & Conditions")
        val termAndConditionEndIndex = termAndConditionStartIndex + "Terms & Conditions".length

        val termAndConditionSpanText = object : ClickableSpan() {
            override fun onClick(p0: View) {
                showToast("Terms & Conditions Clicked")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color =
                    requireContext().getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
            }
        }

        val dataPrivacyStartIndex = agreementText.indexOf("Data Privacy")
        val dataPrivacyEndIndex = dataPrivacyStartIndex + "Data Privacy".length

        val dataPrivacySpanText = object : ClickableSpan() {
            override fun onClick(p0: View) {
                showToast("Data Privacy")
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color =
                    requireContext().getColorFromAttr(com.google.android.material.R.attr.colorPrimary)
            }
        }

        val spannableString = SpannableString(agreementText).apply {
            setSpan(
                termAndConditionSpanText,
                termAndConditionStartIndex,
                termAndConditionEndIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                dataPrivacySpanText,
                dataPrivacyStartIndex,
                dataPrivacyEndIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.textViewAgreement.text = spannableString
    }
}