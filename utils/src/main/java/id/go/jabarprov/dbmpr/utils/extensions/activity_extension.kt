package id.go.jabarprov.dbmpr.utils.extensions

import android.app.Activity

fun Activity.fileProviderAuthority(): String {
    return "${applicationContext.packageName}.fileprovider"
}