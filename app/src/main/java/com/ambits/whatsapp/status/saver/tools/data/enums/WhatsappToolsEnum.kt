package com.ambits.whatsapp.status.saver.tools.data.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ambits.whatsapp.status.saver.tools.R

enum class WhatsappToolsEnum(@DrawableRes val icon: Int, @StringRes val title: Int) {
    WhatsappWeb(R.drawable.ic_whatsapp, R.string.whatsapp_web),
    LiteMessage(R.drawable.ic_chat, R.string.lite_messge),
    StatusSaver(R.drawable.ic_downlaod, R.string.status_saver),
    SelfChat(R.drawable.ic_self_chat, R.string.self_chat),
    TextStyle(R.drawable.ic_text_style, R.string.text_style),
    Setting(R.drawable.ic_setting, R.string.setting)
}