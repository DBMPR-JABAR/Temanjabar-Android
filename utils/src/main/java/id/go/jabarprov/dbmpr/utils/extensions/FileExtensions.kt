package id.go.jabarprov.dbmpr.utils.extensions

import android.app.Activity
import android.net.Uri
import id.go.jabarprov.dbmpr.utils.utils.FileUtils
import java.io.File

fun File.getUri(context: Activity): Uri {
    return FileUtils.getFileUri(context, this)
}
