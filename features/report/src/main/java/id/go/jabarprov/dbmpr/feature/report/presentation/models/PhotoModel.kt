package id.go.jabarprov.dbmpr.feature.report.presentation.models

import android.net.Uri

data class PhotoModel(
    val uri: Uri,
    val isSelected: Boolean = false
)