package id.go.jabarprov.dbmpr.feature.dashboard.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.go.jabarprov.dbmpr.feature.dashboard.databinding.LayoutDashboardMenuItemBinding
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.diff_utils.DashboardMenuItemDiffUtil
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.models.DashboardMenuModel
import id.go.jabarprov.dbmpr.feature.dashboard.presentation.models.DashboardMenuType

class DashboardMenuAdapter(private val spanCount: Int, private val space: Int) :
    ListAdapter<DashboardMenuModel, DashboardMenuAdapter.DashboardMenuItemViewHolder>(
        DashboardMenuItemDiffUtil()
    ) {

    private var onClickListener: ((DashboardMenuType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardMenuItemViewHolder {
        val binding =
            LayoutDashboardMenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        binding.root.updateLayoutParams {
            width = (parent.measuredWidth - (space * (spanCount + 1))) / spanCount
        }
        return DashboardMenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardMenuItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, onClickListener)
        }
    }

    fun setOnClickListener(action: (DashboardMenuType) -> Unit) {
        onClickListener = action
    }

    class DashboardMenuItemViewHolder(private val binding: LayoutDashboardMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            dashboardMenu: DashboardMenuModel,
            onClickListener: ((DashboardMenuType) -> Unit)?
        ) {
            binding.apply {
                root.setOnClickListener {
                    onClickListener?.invoke(dashboardMenu.type)
                }
                imageViewIconMenu.setImageResource(dashboardMenu.icon)
                textViewMenu.text = dashboardMenu.name
            }
        }
    }

}