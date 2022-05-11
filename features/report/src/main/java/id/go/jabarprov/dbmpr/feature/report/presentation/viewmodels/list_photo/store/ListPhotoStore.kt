package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store

import id.go.jabarprov.dbmpr.core_main.store.Store
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListPhotoStore @Inject constructor() :
    Store<ListPhotoAction, ListPhotoState>(ListPhotoState()) {
    override fun reduce(action: ListPhotoAction) {
        coroutineScope.launch {
            when (action) {
                is ListPhotoAction.SetModeDeleteImage -> setModeDeleteImage(action.value)
                is ListPhotoAction.UpdateListPhoto -> updateListPhoto(action.listPhoto)
            }
        }
    }

    private fun setModeDeleteImage(value: Boolean) {
        state.value = state.value.copy(isModeDeleteImage = value)
    }

    private fun updateListPhoto(listPhoto: List<PhotoModel>) {
        state.value = state.value.copy(currentListPhoto = listPhoto)
    }
}