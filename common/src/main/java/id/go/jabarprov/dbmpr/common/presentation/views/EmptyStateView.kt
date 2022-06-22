package id.go.jabarprov.dbmpr.common.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import id.go.jabarprov.dbmpr.common.R

class EmptyStateView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private val mTypedArray by lazy {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.EmptyStateView,
            0,
            0
        )
    }

    private var title = mTypedArray.getString(R.styleable.EmptyStateView_title_empty)
        ?: context.getString(R.string.informasi_tidak_ditemukan)

    private var description = mTypedArray.getString(R.styleable.EmptyStateView_description_empty)
        ?: context.getString(R.string.informasi_tidak_ditemukan_description)

    private lateinit var mTitleTextView: TextView
    private lateinit var mDescriptionTextView: TextView

    init {
        initView()
    }

    private fun initView() {
        setPadding(48)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_empty_state_view, this, true)

        mTitleTextView = view.findViewById(R.id.text_view_title)
        mDescriptionTextView = view.findViewById(R.id.text_view_desc)

        mTitleTextView.text = title
        mDescriptionTextView.text = description
    }

}