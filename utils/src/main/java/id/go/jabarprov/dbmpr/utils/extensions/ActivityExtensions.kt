package id.go.jabarprov.dbmpr.utils.extensions

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.fileProviderAuthority(): String {
    return "${applicationContext.packageName}.fileprovider"
}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}