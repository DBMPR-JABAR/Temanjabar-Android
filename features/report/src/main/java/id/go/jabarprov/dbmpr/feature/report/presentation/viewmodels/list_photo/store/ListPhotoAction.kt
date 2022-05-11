package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store

import id.go.jabarprov.dbmpr.core_main.store.Action
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

sealed class ListPhotoAction : Action {
    data class SetModeDeleteImage(val value: Boolean) : ListPhotoAction()
    data class UpdateListPhoto(val listPhoto: List<PhotoModel>) : ListPhotoAction()
}