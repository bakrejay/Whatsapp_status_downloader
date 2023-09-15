package com.ambits.whatsapp.status.saver.tools.ui.screens.select_whatsapp_type

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.tools.ui.screens.home.HomeScreen
import com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver.StatusSaverActivity
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.KEY_WHATSAPP_TYPE

class SelectWhatsappTypeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelectWhatsappType(
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                },
                onWhatsappToolsItemClick = { whatsappToolsEnum ->
                    startActivity(
                        Intent(
                            this@SelectWhatsappTypeActivity,
                            StatusSaverActivity::class.java
                        ).apply {
                            putExtra(KEY_WHATSAPP_TYPE,whatsappToolsEnum)
                        }
                    )
                }
            )
        }
    }
}
