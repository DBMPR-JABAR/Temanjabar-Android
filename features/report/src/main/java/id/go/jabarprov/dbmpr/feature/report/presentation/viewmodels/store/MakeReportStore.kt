package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.store

import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.report.presentation.models.CategoryReportModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeReportStore @Inject constructor() :
    Store<MakeReportAction, MakeReportState>(MakeReportState()) {
    override fun reduce(action: MakeReportAction) {
        coroutineScope.launch {
            when (action) {
                is MakeReportAction.UpdateSearchKeyword -> updateListBySearchKeyword(action.keyword)
                is MakeReportAction.UpdateSelectedCategory -> updateSelectedCategory(action.category)
            }
        }
    }

    private fun updateListBySearchKeyword(keyword: String) {
        state.value = state.value.copy(
            currentList = if (keyword.isEmpty()) {
                MakeReportState.LIST_CATEGORY_REPORT
                    .map {
                        it.copy(isSelected = it.id == state.value.selectedCategory?.id)
                    }
            } else {
                MakeReportState.LIST_CATEGORY_REPORT
                    .map {
                        it.copy(isSelected = it.id == state.value.selectedCategory?.id)
                    }
                    .filter {
                        it.description.contains(keyword, ignoreCase = true)
                    }
            }
        )
    }

    private fun updateSelectedCategory(category: CategoryReportModel) {
        state.value = state.value.copy(
            selectedCategory = category,
            currentList = state.value.currentList.map {
                it.copy(isSelected = it.id == category.id)
            }
        )
    }
}