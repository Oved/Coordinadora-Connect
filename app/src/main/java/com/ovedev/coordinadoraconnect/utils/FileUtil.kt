package com.ovedev.coordinadoraconnect.utils

import android.os.Environment
import com.ovedev.coordinadoraconnect.CoordinadoraConnectApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class FileUtil @Inject constructor(
    private val applicationContext: CoordinadoraConnectApp
) {

    fun saveFileInLocalBase64(fileBase64: String, fileName: String): File? {

        val downloadsDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "pruebacoordi")
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val file = File(downloadsDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            outputStream.write(fileBase64.decodeBase64ToBytes())
            outputStream.close()
            file

        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

    }

}