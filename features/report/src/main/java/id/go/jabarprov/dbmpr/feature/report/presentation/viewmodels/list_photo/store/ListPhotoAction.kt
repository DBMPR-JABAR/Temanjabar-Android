package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store

import id.go.jabarprov.dbmpr.core_main.store.Action
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

sealed class ListPhotoAction : Action {
    data class SetModeDeleteImage(val value: Boolean) : ListPhotoAction()
    data class InitializePhoto(val listPhoto: List<PhotoModel>) : ListPhotoAction()
    data class SelectPhoto(val photoModel: PhotoModel) : ListPhotoAction()
    data class UnselectPhoto(val photoModel: PhotoModel) : ListPhotoAction()
}