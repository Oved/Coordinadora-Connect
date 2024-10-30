package com.ovedev.coordinadoraconnect.utils

import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.ovedev.coordinadoraconnect.CoordinadoraConnectApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class FileUtil @Inject constructor(
    private val applicationContext: CoordinadoraConnectApp
) {

    fun saveFileInLocalBase64(fileBase64: String, fileName: String): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveFileInDownloadsScopedStorage(fileBase64, fileName)
        } else {
            saveFileInDownloadsLegacy(fileBase64, fileName)?.let { Uri.fromFile(it) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveFileInDownloadsScopedStorage(fileBase64: String, fileName: String): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/${Constant.PACKAGE_NAME}")
        }

        val resolver = applicationContext.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)

        return try {
            uri?.let {
                resolver.openOutputStream(uri)?.use { outputStream ->
                    outputStream.write(android.util.Base64.decode(fileBase64, android.util.Base64.DEFAULT))
                }
            }
            uri
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveFileInDownloadsLegacy(fileBase64: String, fileName: String): File? {
        val downloadsDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Constant.PACKAGE_NAME)
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val file = File(downloadsDir, fileName)

        return try {
            FileOutputStream(file).use { outputStream ->
                outputStream.write(android.util.Base64.decode(fileBase64, android.util.Base64.DEFAULT))
            }
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}