package id.go.jabarprov.dbmpr.feature.report.presentation.viewmodels.list_photo.store

import id.go.jabarprov.dbmpr.core_main.store.State
import id.go.jabarprov.dbmpr.feature.report.presentation.models.PhotoModel

data class ListPhotoState(
    val isModeDeleteImage: Boolean = false,
    val currentListPhoto: List<PhotoModel> = listOf()
) : State