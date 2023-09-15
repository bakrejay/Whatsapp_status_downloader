package com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.ui.components.StatusItemVideoView
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status

@Composable
fun VideoStatusView(videoStatusList: ArrayList<Status>, onStatusItemClick: (Status) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        content = {
            items(videoStatusList) { videoStatus ->
                StatusItemVideoView(videoStatus, onStatusItemClick)
            }
        })
}