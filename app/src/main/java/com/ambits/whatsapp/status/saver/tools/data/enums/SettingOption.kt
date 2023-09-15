package com.ambits.whatsapp.status.saver.tools.data.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

enum class SettingOption(
    val title: String,
    val iconRes: ImageVector
) {
    Share("Share", Icons.Outlined.Share),
    RateUs("Rate Us", Icons.Outlined.ThumbUp),
    PrivacyPolicy("Privacy Policy", Icons.Outlined.PrivacyTip),
    ContactUs("Contact Us", Icons.Outlined.Phone),
}