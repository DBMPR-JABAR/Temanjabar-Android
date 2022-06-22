package id.go.jabarprov.dbmpr.common.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.common.R

class RecyclerStateView(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mEmptyStateView: EmptyStateView
    private lateinit var mErrorStateView: ErrorStateView

    init {
        initView()
    }

    private fun initView() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_recycler_state_view, this, true)

        mRecyclerView = view.findViewById(R.id.recycler_view)
        mEmptyStateView =
            view.findViewById<EmptyStateView?>(R.id.empty_state_view).also { it.isVisible = false }
        mErrorStateView =
            view.findViewById<ErrorStateView?>(R.id.error_state_view).also { it.isVisible = false }
    }

}