package com.ambits.whatsapp.status.saver.tools.data.enums

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ambits.whatsapp.status.saver.tools.R
import kotlinx.parcelize.Parcelize
import java.io.Serializable

enum class WhatsappTypeEnum(@DrawableRes val icon: Int, @StringRes val title: Int) : Serializable {
    WhatsApp(R.drawable.ic_whatsapp, R.string.whatsapp),
    WhatsappBusiness(R.drawable.ic_whatsapp, R.string.whatsapp_business),
}