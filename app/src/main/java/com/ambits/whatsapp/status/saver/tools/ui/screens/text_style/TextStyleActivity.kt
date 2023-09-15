package com.ambits.whatsapp.status.saver.tools.ui.screens.text_style

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class TextStyleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextStyleScreen(
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                },
                onWhatsappShare = {
                    shareOnWhatsapp(it)
                },
                onCopyText = {
                    onCopyClick(it)
                },
                onShareText = {
                    onSendClick(it)
                }
            )
        }
    }

    private fun shareOnWhatsapp(text: String) {
        val intentShareList: MutableList<Intent> = ArrayList()
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        val resolveInfoList = packageManager.queryIntentActivities(shareIntent, 0)
        for (resInfo in resolveInfoList) {
            val packageName = resInfo.activityInfo.packageName
            val name = resInfo.activityInfo.name
            if (packageName.contains("com.whatsapp.w4b") ||
                packageName.contains("com.whatsapp")
            ) {
                val intent = Intent()
                intent.component = ComponentName(packageName, name)
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(
                    Intent.EXTRA_TEXT, text
                )
                intentShareList.add(intent)
            }
        }
        if (intentShareList.isEmpty()) {
            Toast.makeText(
                this@TextStyleActivity,
                "No Whatsapp found to share !",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val chooserIntent = Intent.createChooser(intentShareList.removeAt(0), "Share via")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentShareList.toTypedArray())
            startActivity(chooserIntent)
        }
    }

    private fun onSendClick(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Share via"))
    }

    private fun onCopyClick(text: String) {
        val cData = ClipData.newPlainText(text, text)
        (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(cData)
    }
}