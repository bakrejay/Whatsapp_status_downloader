package com.ambits.whatsapp.status.saver.tools.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsappToolItem
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappToolsEnum
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme

@Composable
fun HomeScreen(
    onBackPress: () -> Unit,
    onTailIconPress: () -> Unit,
    onWhatsappToolClick: (WhatsappToolsEnum) -> Unit
) {
    val whatsappToolsList = listOf(
        WhatsappToolsEnum.WhatsappWeb,
        WhatsappToolsEnum.LiteMessage,
        WhatsappToolsEnum.StatusSaver,
        WhatsappToolsEnum.TextStyle,
        WhatsappToolsEnum.Setting
    )
    WhatsappStatusSaverToolsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            WhatsStatusAppBar(
                title = "Whatsapp Tools",
                isShowBackButton = false,
                tailIcon = R.drawable.ic_info,
                onBackPress = {
                    onBackPress()
                },
                onTailIconPress = {
                    onTailIconPress()
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                content = {
                    items(whatsappToolsList) { item: WhatsappToolsEnum ->
                        WhatsappToolItem(
                            item,
                            onItemClick = { whatsappToolsEnum ->
                                onWhatsappToolClick(whatsappToolsEnum)
                            }
                        )
                    }
                })
        }
    }
}

@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onBackPress = {

        },
        onTailIconPress = {

        },
        onWhatsappToolClick = { whatsappToolsEnum ->

        }
    )
}

