package id.go.jabarprov.dbmpr.utils.extensions

import java.util.*

fun String.capitalizeEachWord(): String {
    return this.lowercase().split(" ").joinToString(separator = " ") { word ->
        word.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}