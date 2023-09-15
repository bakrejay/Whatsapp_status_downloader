package com.ambits.whatsapp.status.saver.tools.ui.screens.lite_message

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class LiteMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiteMessageScreen(
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                },
                onSendMessage = {phoneNumber: String, message: String ->
                    sendWhatsappMessageToNumber(
                        phoneNumber,
                        message
                    )
                }
            )
        }
    }

    private fun sendWhatsappMessageToNumber(phoneNumber: String, message: String) {
        val intentShareList: MutableList<Intent> = ArrayList()
        val shareIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                String.format(
                    "https://api.whatsapp.com/send?phone=%s&text=%s",
                    phoneNumber,
                    message
                )
            )
        )
        val resolveInfoList = packageManager.queryIntentActivities(shareIntent, 0)
        for (resInfo in resolveInfoList) {
            val packageName = resInfo.activityInfo.packageName
            val name = resInfo.activityInfo.name
            if (packageName.contains("com.whatsapp.w4b") ||
                packageName.contains("com.whatsapp")
            ) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            phoneNumber,
                            message
                        )
                    )
                )
                intent.component = ComponentName(packageName, name)
                intentShareList.add(intent)
            }
        }
        if (intentShareList.isEmpty()) {
            Toast.makeText(
                this@LiteMessageActivity,
                "No Whatsapp found to share !",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val chooserIntent = Intent.createChooser(intentShareList.removeAt(0), "Share via")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentShareList.toTypedArray())
            startActivity(chooserIntent)
        }
    }
}