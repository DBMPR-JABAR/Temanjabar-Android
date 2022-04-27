package id.go.jabarprov.dbmpr.utils.extensions

import android.view.ViewGroup

fun ViewGroup.setEnabledRecursive(value: Boolean) {
    isEnabled = value
    for (i in 0..childCount) {
        val view = getChildAt(i)
        if (view != null) {
            view.isEnabled = value
            if (view is ViewGroup) {
                view.setEnabled(value)
            }
        }
    }
}