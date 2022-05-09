package id.go.jabarprov.dbmpr.common.presentation.widgets

import androidx.fragment.app.DialogFragment
import id.go.jabarprov.dbmpr.common.R

open class BaseDialogFragment : DialogFragment() {
    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawableResource(R.drawable.bg_dialog)
        }
    }
}