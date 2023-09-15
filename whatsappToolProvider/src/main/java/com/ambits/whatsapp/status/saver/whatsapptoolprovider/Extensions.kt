package com.gb.wapp.tool.wapptoolsprovider

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.getWhatsappBusinessFolder
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.getWhatsappFolder

fun <T> Activity.navigateToActivity(
    activityClass: Class<T>,
    bundle: Bundle? = null,
    isNeedToFinish: Boolean = false,
) {
    startActivity(
        Intent(
            this,
            activityClass
        ).apply {
            bundle?.let {
                putExtras(bundle)
            }
        }
    )
    if (isNeedToFinish) finish()
}

fun Activity.makeStatusBarTransparentWithFullSizeWindowHeight() {
    window.statusBarColor = android.graphics.Color.TRANSPARENT
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        val insetsController = window.insetsController
        if (insetsController != null) {
            insetsController.setSystemBarsAppearance(
                android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            window.setFlags(
                android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
                android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        window.setDecorFitsSystemWindows(true)
    }
    val decorView: View = window.decorView
    val uiOptions: Int = (android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    decorView.systemUiVisibility = uiOptions
}

fun Context.navigateToPermissionSettingScreen() {
    val intent = Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:$packageName")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    startActivity(intent)
}

@RequiresApi(Build.VERSION_CODES.R)
fun Context.doIntentForWhatsapp(resultLauncher: ActivityResultLauncher<Intent>) {
    doIntentForLocation(getWhatsappFolder(),resultLauncher)
}

@RequiresApi(Build.VERSION_CODES.R)
fun Context.doIntentForWhatsappWeb(resultLauncher: ActivityResultLauncher<Intent>) {
    doIntentForLocation(getWhatsappBusinessFolder(),resultLauncher)
}

@RequiresApi(Build.VERSION_CODES.R)
fun Context.doIntentForLocation(statusDir: String, resultLauncher: ActivityResultLauncher<Intent>) {
    val sm = (getSystemService(Context.STORAGE_SERVICE) as StorageManager)
    val intent = sm.primaryStorageVolume.createOpenDocumentTreeIntent()
    val scheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        (intent.getParcelableExtra(
            DocumentsContract.EXTRA_INITIAL_URI, Uri::class.java
        ) as Uri).toString().replace("/root/", "/document/")
    } else {
        (intent.getParcelableExtra<Uri>(DocumentsContract.EXTRA_INITIAL_URI) as Uri).toString()
            .replace("/root/", "/document/")
    }
    intent.putExtra(
        DocumentsContract.EXTRA_INITIAL_URI, Uri.parse("$scheme%3A$statusDir")
    )
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    resultLauncher.launch(intent)
}

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}