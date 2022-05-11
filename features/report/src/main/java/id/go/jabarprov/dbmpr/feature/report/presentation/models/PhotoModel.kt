package id.go.jabarprov.dbmpr.feature.report.presentation.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    val uri: Uri,
    val isSelected: Boolean = false
) : Parcelable