@file:OptIn(ExperimentalMaterial3Api::class)

package com.ambits.whatsapp.status.saver.tools.ui.screens.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.data.enums.SettingOption
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme

@Composable
fun SettingScreen(
    onBackPress: () -> Unit,
    onOptionClick: (SettingOption) -> Unit
) {
    WhatsappStatusSaverToolsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            WhatsStatusAppBar(
                title = "Setting",
                onBackPress = {
                    onBackPress()
                },
                onTailIconPress = {}
            )
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    listOf(
                        SettingOption.Share,
                        SettingOption.RateUs,
                        SettingOption.PrivacyPolicy,
                        SettingOption.ContactUs
                    )
                ) { settingOption: SettingOption ->
                    SettingOptionItem(
                        settingOption = settingOption,
                        onOptionClick = onOptionClick
                    )
                }
            }
        }
    }
}

@Composable
fun SettingOptionItem(settingOption: SettingOption, onOptionClick: (SettingOption) -> Unit) {
    Card(
        onClick = {
            onOptionClick(settingOption)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = settingOption.iconRes,
                contentDescription = settingOption.title,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = settingOption.title)
        }
    }

}
