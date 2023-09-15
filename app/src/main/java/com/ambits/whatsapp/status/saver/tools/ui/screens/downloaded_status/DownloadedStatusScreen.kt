package com.ambits.whatsapp.status.saver.tools.ui.screens.downloaded_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver.ImageStatusView
import com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver.VideoStatusView
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status

@Composable
fun DownloadedStatusScreen(
    downloadedStatusUiState: DownloadedStatusUiState,
    onBackPress: () -> Unit,
    onStatusItemClick: (Status) -> Unit
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Images", "Videos")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        WhatsStatusAppBar(
            title = "Downloads",
            onBackPress = {
                onBackPress()
            },
            onTailIconPress = {

            }
        )
        TabRow(
            selectedTabIndex = state,
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = { Text(title) }
                )
            }
        }
        when (state) {
            0 -> ImageStatusView(downloadedStatusUiState.imageStatusList, onStatusItemClick)
            else -> VideoStatusView(downloadedStatusUiState.videoStatusList, onStatusItemClick)
        }
    }
}