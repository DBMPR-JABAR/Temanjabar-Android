package id.go.jabarprov.dbmpr.utils.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import id.go.jabarprov.dbmpr.utils.extensions.fileProviderAuthority
import java.io.File
import java.util.*

abstract class FileUtils {
    companion object {
        fun getFileUri(context: Activity, file: File): Uri {
            return FileProvider.getUriForFile(context, context.fileProviderAuthority(), file)
        }

        fun createPictureCacheFile(
            context: Context,
            fileName: String = CalendarUtils.formatCalendarToString(
                Calendar.getInstance(),
                pattern = "yyyy_MM_dd_HH_mm_ss"
            )
        ): File {
            val cachePictureDir = File(context.cacheDir, "Pictures")
            if (!cachePictureDir.exists()) {
                cachePictureDir.mkdirs()
            }
            return File(cachePictureDir, "PIC_$fileName.jpeg")
        }

        fun createVideoCacheFile(
            context: Context,
            fileName: String = CalendarUtils.formatCalendarToString(
                Calendar.getInstance(),
                pattern = "yyyy_MM_dd_HH_mm_ss"
            )
        ): File {
            val cacheVideoDir = File(context.cacheDir, "Videos")
            if (!cacheVideoDir.exists()) {
                cacheVideoDir.mkdirs()
            }
            return File(cacheVideoDir, "VID_$fileName.mp4")
        }
    }
}