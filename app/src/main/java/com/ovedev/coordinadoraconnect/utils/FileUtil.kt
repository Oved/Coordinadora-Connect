package com.ovedev.coordinadoraconnect.utils

import android.util.Log
import com.ovedev.coordinadoraconnect.CoordinadoraConnectApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class FileUtil @Inject constructor(
    private val applicationContext: CoordinadoraConnectApp
) {

    fun saveFileInLocalBase64(fileBase64: String, fileName: String): File? {

        val file = File(applicationContext.filesDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            outputStream.write(fileBase64.decodeBase64ToBytes())
            outputStream.close()
            file

        } catch (e: IOException) {
            Log.e("Exc", "saveFileInLocalBase64: ${e.message}")
            null
        }

    }

}