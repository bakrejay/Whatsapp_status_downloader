package com.ambits.whatsapp.status.saver.tools.ui.screens.text_style

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.gb.wapp.tool.wapptoolsprovider.style_text.FontStyle
import com.gb.wapp.tool.wapptoolsprovider.style_text.getStylishTextFromFontStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextStyleScreen(
    onBackPress: () -> Unit,
    onWhatsappShare: (String) -> Unit,
    onShareText: (String) -> Unit,
    onCopyText: (String) -> Unit,
) {
    WhatsappStatusSaverToolsTheme {
        var text by remember {
            mutableStateOf("Text Style")
        }
        val fontStyleList = listOf(
            FontStyle.Bubble,
            FontStyle.Currency,
            FontStyle.Magic,
            FontStyle.Knight,
            FontStyle.Antrophobia,
            FontStyle.FancyStyle1,
            FontStyle.FancyStyle2,
            FontStyle.FancyStyle3,
            FontStyle.FancyStyle4,
            FontStyle.FancyStyle5,
            FontStyle.FancyStyle6,
            FontStyle.FancyStyle7,
            FontStyle.FancyStyle8,
            FontStyle.FancyStyle9,
            FontStyle.FancyStyle10,
            FontStyle.FancyStyle11,
            FontStyle.FancyStyle12,
            FontStyle.FancyStyle13,
            FontStyle.FancyStyle14,
            FontStyle.FancyStyle15,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            WhatsStatusAppBar(
                title = "Text Style",
                onBackPress = {
                    onBackPress()
                },
                onTailIconPress = {}
            )
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Message") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                maxLines = 3
            )
            Text(
                text = "Text Style",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            if (text.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(fontStyleList) { fontStyle: FontStyle ->
                        TextStyleItem(
                            text = text,
                            fontStyle = fontStyle,
                            onWhatsappShare = onWhatsappShare,
                            onCopyText = onCopyText,
                            onShareText = onShareText
                        )
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun TextStyleItem(
    text: String,
    fontStyle: FontStyle,
    onWhatsappShare: (String) -> Unit,
    onShareText: (String) -> Unit,
    onCopyText: (String) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        val styleText = getStylishTextFromFontStyle(text, fontStyle)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
                    .weight(1F),
                text = styleText,
            )
            IconButton(
                onClick = { onWhatsappShare(styleText) },
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_whatsapp),
                    contentDescription = "Whatsapp"
                )
            }
            IconButton(onClick = { onShareText(styleText) }) {
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = "Share"
                )
            }
            IconButton(onClick = { onCopyText(styleText) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "Copy"
                )
            }
        }
    }
}