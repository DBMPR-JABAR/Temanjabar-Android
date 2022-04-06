package id.go.jabarprov.dbmpr.common.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import id.go.jabarprov.dbmpr.common.R

class LoadingDialog private constructor() : BaseDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_loading_dialog, container, false)
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, "Loading Dialog")
    }

    companion object {
        fun create(): LoadingDialog {
            return LoadingDialog().apply { isCancelable = false }
        }
    }

}