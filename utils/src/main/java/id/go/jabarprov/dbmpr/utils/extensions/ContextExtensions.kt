package id.go.jabarprov.dbmpr.utils.extensions

import android.content.Context
import id.go.jabarprov.dbmpr.utils.utils.CalendarUtils
import java.io.File
import java.util.*

fun Context.createPictureCacheFile(
    fileName: String = CalendarUtils.formatCalendarToString(
        Calendar.getInstance(),
        pattern = "yyyy_MM_dd_HH_mm_ss"
    )
): File {
    val cachePictureDir = File(cacheDir, "Pictures")
    if (!cachePictureDir.exists()) {
        cachePictureDir.mkdirs()
    }
    return File(cachePictureDir, "$fileName.jpeg")
}