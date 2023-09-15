package com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

fun Context.shareApp(appName: String) {
    try {
        val intent = Intent("android.intent.action.SEND")
        intent.type = "text/plain"
        intent.putExtra("android.intent.extra.SUBJECT", appName)
        intent.putExtra(
            "android.intent.extra.TEXT",
            "\nLet me recommend you this application\n\nhttps://play.google.com/store/apps/details?id=$packageName\n"
        )
        startActivity(Intent.createChooser(intent, "choose one"))
    } catch (unused: Exception) {
        unused.printStackTrace()
    }
}

fun Context.gotoPlayStore() {
    try {
        startActivity(
            Intent(
                "android.intent.action.VIEW",
                Uri.parse("market://details?id=$packageName")
            )
        )
    } catch (unused: ActivityNotFoundException) {
        startActivity(
            Intent(
                "android.intent.action.VIEW",
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun Context.contactUs(contactMail: String) {
    val uriText = "mailto:$contactMail" +
            "?subject=" + "Contact Us" +
            "&body=" + " "
    val intent = Intent(
        Intent.ACTION_SENDTO
    ).apply {
        data = Uri.parse(uriText)
    }
    startActivity(Intent.createChooser(intent, "Choose an Email client :"))
}

fun Context.isWhatsappInstalled() = appInstalledOrNot("com.whatsapp")

fun Context.isWhatsappBusinessInstalled() = appInstalledOrNot("com.whatsapp.w4b")

private fun Context.appInstalledOrNot(uri: String?): Boolean {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}