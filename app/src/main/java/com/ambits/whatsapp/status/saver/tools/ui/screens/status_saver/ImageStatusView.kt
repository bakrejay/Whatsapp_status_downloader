package com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.ui.components.StatusItemImageView
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.util.ArrayList

@Composable
fun ImageStatusView(imageStatusList: ArrayList<Status>,onStatusItemClick: (Status) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        content = {
            items(imageStatusList) { imageStatus ->
                StatusItemImageView(imageStatus,onStatusItemClick)
            }
        })
}