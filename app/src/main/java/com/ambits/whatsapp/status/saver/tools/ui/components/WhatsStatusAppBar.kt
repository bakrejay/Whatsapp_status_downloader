package com.ambits.whatsapp.status.saver.tools.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme

@Composable
fun WhatsStatusAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    isShowBackButton: Boolean = true,
    @DrawableRes tailIcon: Int? = null,
    onBackPress: () -> Unit,
    onTailIconPress: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onBackPress() },
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary),
            enabled = isShowBackButton
        ) {
            if (isShowBackButton) {
                Icon(
                    painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.back),
                )
            }
        }
        Text(
            text = title ?: "",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )

        IconButton(
            onClick = { onTailIconPress() },
            colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary),
            enabled = tailIcon != null
        ) {
            tailIcon?.let { tailIcon ->
                Icon(
                    painterResource(id = tailIcon),
                    contentDescription = "",
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WhatsStatusAppBarPreview() {
    WhatsappStatusSaverToolsTheme {
        WhatsStatusAppBar(
            title = "Whatsapp Status",
            onBackPress = {

            },
            tailIcon = R.drawable.ic_down,
            onTailIconPress = {

            }
        )
    }
}
