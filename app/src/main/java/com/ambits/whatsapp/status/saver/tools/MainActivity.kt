package com.ambits.whatsapp.status.saver.tools

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappToolsEnum
import com.ambits.whatsapp.status.saver.tools.ui.screens.home.HomeScreen
import com.ambits.whatsapp.status.saver.tools.ui.screens.lite_message.LiteMessageActivity
import com.ambits.whatsapp.status.saver.tools.ui.screens.select_whatsapp_type.SelectWhatsappTypeActivity
import com.ambits.whatsapp.status.saver.tools.ui.screens.setting.SettingActivity
import com.ambits.whatsapp.status.saver.tools.ui.screens.text_style.TextStyleActivity
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.whappWeb.WappWebActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                onBackPress = {},
                onTailIconPress = {

                },
                onWhatsappToolClick = { whatsappToolsEnum ->
                    when(whatsappToolsEnum){
                        WhatsappToolsEnum.WhatsappWeb -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    WappWebActivity::class.java
                                )
                            )
                        }
                        WhatsappToolsEnum.LiteMessage -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    LiteMessageActivity::class.java
                                )
                            )
                        }
                        WhatsappToolsEnum.StatusSaver -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    SelectWhatsappTypeActivity::class.java
                                )
                            )
                        }
                        WhatsappToolsEnum.SelfChat -> {}
                        WhatsappToolsEnum.TextStyle -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    TextStyleActivity::class.java
                                )
                            )
                        }
                        WhatsappToolsEnum.Setting -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    SettingActivity::class.java
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}
