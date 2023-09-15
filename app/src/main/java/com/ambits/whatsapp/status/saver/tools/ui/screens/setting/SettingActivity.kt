package com.ambits.whatsapp.status.saver.tools.ui.screens.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.data.enums.SettingOption
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.contactUs
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.gotoPlayStore
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.shareApp

class SettingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingScreen(
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                },
                onOptionClick = { settingOption ->
                    onOptionItemClick(settingOption)
                }
            )
        }
    }

    private fun onOptionItemClick(settingOption: SettingOption) {
        when (settingOption) {
            SettingOption.Share -> {
                shareApp(getString(R.string.app_name))
            }

            SettingOption.RateUs -> {
                gotoPlayStore()
            }

            SettingOption.PrivacyPolicy -> {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://sites.google.com/view/privacypolicyforwhatsappstatus/home")
                })
            }

            SettingOption.ContactUs -> {
                contactUs("ambitsitsolutions@gmail.com")
            }
        }
    }
}