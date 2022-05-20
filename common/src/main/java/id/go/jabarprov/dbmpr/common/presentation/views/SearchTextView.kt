package id.go.jabarprov.dbmpr.common.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import id.go.jabarprov.dbmpr.common.R

class SearchTextView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    val editText get() = mEditText
    val searchButton get() = mSearchButton

    private val mTypedArray by lazy {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.SearchTextView,
            0,
            0
        )
    }

    private lateinit var mEditText: EditText
    private lateinit var mSearchButton: Button

    private var hintText = mTypedArray.getString(R.styleable.SearchTextView_hint) ?: ""

    init {
        initUI()
    }

    private fun initUI() {
        setBackgroundResource(R.drawable.bg_container_gray_100_solid)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_search_text_view, this, true)

        mEditText = view.findViewById(R.id.edit_text_search_category)
        mSearchButton = view.findViewById(R.id.button_search)

        mEditText.apply {
            hint = hintText
        }
    }

}