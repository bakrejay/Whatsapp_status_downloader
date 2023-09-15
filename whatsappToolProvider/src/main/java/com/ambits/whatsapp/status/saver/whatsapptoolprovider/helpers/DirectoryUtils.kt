package com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object DirectoryUtils {
    const val VIDEO = "video"

    fun getWhatsappFolder(): String {
        return if (File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "Android/media/com.whatsapp/WhatsApp" + File.separator + "Media" + File.separator + ".Statuses"
            ).isDirectory
        ) {
            "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"
        } else "WhatsApp%2FMedia%2F.Statuses"
    }

    fun getWhatsappBusinessFolder(): String {
        return if (File(
                Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses"
            ).isDirectory
        ) {
            "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses"
        } else "WhatsApp Business%2FMedia%2F.Statuses"
    }

    val STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp/Media/.Statuses"
    )

    val BUSINESS_STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp Business/Media/.Statuses"
    )

    val STATUS_DIRECTORY_NEW = File(
        (Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
    )

    val BUSINESS_STATUS_DIRECTORY_NEW = File(
        (Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses")
    )

    fun saveFile(status: Status, context: Context, nameRes: Int) {
        if (DocumentFile.fromSingleUri(context, Uri.parse(status.path))!!.exists()) {
            if (download(context, status, nameRes)) {
                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Something went wrong..!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun download(context: Context, status: Status, nameRes: Int): Boolean {
        return copyFileInSavedDir(context, status, nameRes)
    }

    private fun copyFileInSavedDir(context: Context, status: Status, nameRes: Int): Boolean {
        val finalPath: String = if (status.isVideo) {
            getDir(context, "Videos", nameRes).absolutePath
        } else {
            getDir(context, "Images", nameRes).absolutePath
        }
        if (File(finalPath).exists().not()) {
            File(finalPath).mkdir()
        }
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())
        val fileName = if (status.isVideo) {
            "VID_$currentDateTime.mp4"
        } else {
            "IMG_$currentDateTime.jpg"
        }
        val file = File(finalPath + File.separator + fileName)
        if (file.exists().not()) {
            file.createNewFile()
        }
        val destUri = Uri.fromFile(File(finalPath + File.separator + fileName))
        try {
            val `is` = context.contentResolver.openInputStream(Uri.parse(status.path))
            val os = context.contentResolver.openOutputStream(destUri, "w")
            val buffer = ByteArray(1024)
            while (true) {
                val read = `is`!!.read(buffer)
                if (read > 0) {
                    os!!.write(buffer, 0, read)
                } else {
                    `is`.close()
                    os!!.flush()
                    os.close()
                    val intent = Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE")
                    intent.data = destUri
                    context.sendBroadcast(intent)
                    return true
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    private fun getDir(context: Context, folder: String, nameRes: Int): File {
        val rootFile = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "Download" + File.separator + context.resources.getString(
                nameRes
            ) + File.separator + folder
        )
        rootFile.mkdirs()
        return rootFile
    }

    fun copyFile(status: Status, context: Context, nameRes: Int) {
        val finalPath: String = if (status.isVideo) {
            getDir(context, "Videos", nameRes).absolutePath
        } else {
            getDir(context, "Images", nameRes).absolutePath
        }
        val file = File(finalPath)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        val fileName: String
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())
        fileName = if (status.isVideo) {
            "VID_$currentDateTime.mp4"
        } else {
            "IMG_$currentDateTime.jpg"
        }
        val destFile = File(file.toString() + File.separator + fileName)
        try {
            org.apache.commons.io.FileUtils.copyFile(status.file, destFile)
            destFile.setLastModified(System.currentTimeMillis())
            SingleMediaScanner(
                context,
                file
            )
            Toast.makeText(context, "Saved success", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}