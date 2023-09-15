package com.ambits.whatsapp.status.saver.tools.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappToolsEnum
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsappTypeItem(whatsappToolsEnum: WhatsappTypeEnum,onWhatsappToolsItemClick:(WhatsappTypeEnum) -> Unit) {
    Card(
        onClick = { onWhatsappToolsItemClick (whatsappToolsEnum)}, modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = whatsappToolsEnum.icon),
                contentDescription = stringResource(
                    id = whatsappToolsEnum.title
                ),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(id = whatsappToolsEnum.title), fontWeight = FontWeight(500))
        }
    }
}


@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WhatsappTypeItemPreview() {
    WhatsappStatusSaverToolsTheme {
        WhatsappTypeItem(
            WhatsappTypeEnum.WhatsApp
        ){

        }
    }
}