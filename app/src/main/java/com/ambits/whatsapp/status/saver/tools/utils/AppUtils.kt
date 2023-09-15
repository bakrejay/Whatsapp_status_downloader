package com.ambits.whatsapp.status.saver.tools.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.io.File

fun ComponentActivity.setFullScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        hideSystemUiApi30()
        window.insetsController?.addOnControllableInsetsChangedListener { _, status ->
            if (status == 11) {
                Handler(Looper.getMainLooper()).postDelayed({
                    hideSystemUiApi30()
                }, 2000L)
            }
        }
    } else {
        hideSystemUi()
        @Suppress("DEPRECATION")
        window.decorView.setOnSystemUiVisibilityChangeListener { status ->
            if (status == 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    hideSystemUi()
                }, 2000L)
            }
        }
    }
}

@Suppress("DEPRECATION")
private fun ComponentActivity.hideSystemUi() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)

    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

@RequiresApi(Build.VERSION_CODES.R)
private fun ComponentActivity.hideSystemUiApi30() {
    window.insetsController?.hide(WindowInsets.Type.statusBars())
    window.insetsController?.hide(WindowInsets.Type.systemBars())
    window.insetsController?.hide(WindowInsets.Type.systemGestures())
    if (Build.VERSION.SDK_INT >= 34) {
        window.insetsController?.hide(WindowInsets.Type.systemOverlays())
    }
}

fun Context.getBitmapFromFilePath(path: String): Bitmap? {
    return try {
        if (path.contains("content")) {
            MediaStore.Images.Media.getBitmap(
                contentResolver,
                Uri.parse(path)
            )
        } else {
            BitmapFactory.decodeFile(path, BitmapFactory.Options())
        }
    } catch (_: Exception) {
        null
    }
}

fun Context.shareFile(status: Status) {
    val share = Intent()
    share.action = "android.intent.action.SEND"
    share.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (status.isVideo) {
        share.type = "Video/*"
    } else {
        share.type = "image/*"
    }
    val uri: Uri = if (status.path.startsWith("content")) {
        Uri.parse(status.path)
    } else {
        FileProvider.getUriForFile(
            this,
            applicationContext.packageName + ".provider",
            File(status.path)
        )
    }
    share.putExtra("android.intent.extra.STREAM", uri)
    startActivity(share)
}

@Suppress("DEPRECATION")
@SuppressLint("QueryPermissionsNeeded")
fun ComponentActivity.shareImage(status: Status) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    shareIntent.type = if (status.isVideo) "video/*" else "image/jpg"
    val imageUri: Uri = FileProvider.getUriForFile(
        this,
        AppConstants.APP_FILE_PROVIDER,
        File(status.path)
    )
    shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
    val chooser = Intent.createChooser(shareIntent, "")
    val resInfoList: List<ResolveInfo> =
        this.packageManager.queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)
    for (resolveInfo in resInfoList) {
        val packageName: String = resolveInfo.activityInfo.packageName
        grantUriPermission(
            packageName,
            imageUri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }
    startActivity(chooser)
}