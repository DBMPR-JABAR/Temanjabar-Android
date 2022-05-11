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
                is ListPhotoAction.InitializePhoto -> initListPhoto(action.listPhoto)
                is ListPhotoAction.SelectPhoto -> selectPhoto(action.photoModel)
                is ListPhotoAction.UnselectPhoto -> unselectPhoto(action.photoModel)
            }
        }
    }

    private fun setModeDeleteImage(value: Boolean) {
        state.value = state.value.copy(isModeDeleteImage = value)
    }

    private fun initListPhoto(listPhoto: List<PhotoModel>) {
        state.value = state.value.copy(initialListPhoto = listPhoto, currentListPhoto = listPhoto)
    }

    private fun updateListPhoto(listPhoto: List<PhotoModel>) {
        state.value = state.value.copy(currentListPhoto = listPhoto)
    }

    private fun selectPhoto(photoModel: PhotoModel) {
        val newList = state.value.currentListPhoto.map {
            if (photoModel.uri == it.uri) {
                it.copy(isSelected = true)
            } else {
                it
            }
        }
        updateListPhoto(newList)
    }

    private fun unselectPhoto(photoModel: PhotoModel) {
        val newList = state.value.currentListPhoto.map {
            if (photoModel.uri == it.uri) {
                it.copy(isSelected = false)
            } else {
                it
            }
        }
        updateListPhoto(newList)
    }
}