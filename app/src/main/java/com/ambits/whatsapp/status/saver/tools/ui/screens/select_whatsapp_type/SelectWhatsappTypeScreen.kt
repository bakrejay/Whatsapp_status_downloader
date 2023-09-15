package com.ambits.whatsapp.status.saver.tools.ui.screens.select_whatsapp_type

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsappTypeItem
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme

@Composable
fun SelectWhatsappType(
    onBackPress: () -> Unit,
    onWhatsappToolsItemClick:(WhatsappTypeEnum) -> Unit
) {
    val whatsappToolsList = listOf(
        WhatsappTypeEnum.WhatsApp,
        WhatsappTypeEnum.WhatsappBusiness,
    )
    WhatsappStatusSaverToolsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            WhatsStatusAppBar(
                title = "Whatsapp Tools",
                onBackPress = {
                    onBackPress()
                },
                onTailIconPress = {}
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                content = {
                    items(whatsappToolsList) { item: WhatsappTypeEnum ->
                        WhatsappTypeItem(
                            item
                        ){ whatsappToolsEnum ->
                            onWhatsappToolsItemClick(whatsappToolsEnum)
                        }
                    }
                })
        }
    }
}


@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SelectWhatsappTypePreview() {
    WhatsappStatusSaverToolsTheme {
        SelectWhatsappType(
            onBackPress = {

            },
            onWhatsappToolsItemClick = {

            }
        )
    }
}