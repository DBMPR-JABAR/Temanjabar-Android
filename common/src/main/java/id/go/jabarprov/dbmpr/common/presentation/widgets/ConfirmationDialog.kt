package id.go.jabarprov.dbmpr.common.presentation.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import id.go.jabarprov.dbmpr.common.databinding.LayoutConfirmationDialogBinding

class ConfirmationDialog private constructor() : BaseDialogFragment() {

    private var title: String = "Konfirmasi"
    private var description: String = "Apakah anda yakin?"
    private var onPositiveButtonClickListener: () -> Unit = {}
    private var onNegativeButtonClickListener: () -> Unit = {}

    internal lateinit var binding: LayoutConfirmationDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutConfirmationDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            textViewTitleDialog.text = title
            textViewDescriptionDialog.text = description
            buttonPositive.setOnClickListener {
                onPositiveButtonClickListener()
                dismiss()
            }
            buttonNegative.setOnClickListener {
                onNegativeButtonClickListener()
                dismiss()
            }
        }
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, "Confirmation Dialog")
    }

    class Builder {

        private val confirmationDialog by lazy { ConfirmationDialog() }

        fun setTitle(title: String): Builder {
            confirmationDialog.title = title
            return this
        }

        fun setDescription(description: String): Builder {
            confirmationDialog.description = description
            return this
        }

        fun setOnPositiveButtonClickListener(action: () -> Unit): Builder {
            confirmationDialog.onPositiveButtonClickListener = action
            return this
        }

        fun setOnNegativeButtonClickListener(action: () -> Unit): Builder {
            confirmationDialog.onNegativeButtonClickListener = action
            return this
        }

        fun build(): ConfirmationDialog {
            return confirmationDialog
        }
    }
}