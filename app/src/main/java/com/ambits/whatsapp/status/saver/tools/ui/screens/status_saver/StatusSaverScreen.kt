package com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.tools.ui.components.InformationBox
import com.ambits.whatsapp.status.saver.tools.ui.components.WhatsStatusAppBar
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.util.ArrayList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusSaverScreen(
    statusSaverUiState: StatusSaverUiState,
    onBackPress: () -> Unit,
    onStatusItemClick: (Status) -> Unit,
    onShowDownloadClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        floatingActionButton = {
            FloatingActionButton(onClick = { onShowDownloadClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_down),
                    "Floating action button."
                )
            }
        }
    ) { safePadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(safePadding)
        ) {
            WhatsStatusAppBar(
                title = "Status Saver",
                onBackPress = {
                    onBackPress()
                },
                onTailIconPress = {

                }
            )
            when (statusSaverUiState.whatsappStatusType) {
                WhatsappTypeEnum.WhatsApp -> {
                    statusSaverUiState.isWhatsappInstalled?.let {
                        if (!statusSaverUiState.isWhatsappInstalled) {
                            NoWhatsappInstalledView(statusSaverUiState.whatsappStatusType)
                        }
                    }
                    statusSaverUiState.isNoWhatsappStatusFound?.let {
                        InformationBox(
                            title = "No Status found",
                            message = "You must need to watch status to save."
                        )
                    }
                    statusSaverUiState.isStatusFound?.let { isStatusFound ->
                        if (isStatusFound) {
                            StatusView(
                                statusSaverUiState.imageStatusList,
                                statusSaverUiState.videoStatusList,
                                onStatusItemClick
                            )
                        }
                    }

                }

                WhatsappTypeEnum.WhatsappBusiness -> {
                    statusSaverUiState.isWhatsappBusinessInstalled?.let {
                        if (!statusSaverUiState.isWhatsappBusinessInstalled) {
                            NoWhatsappInstalledView(statusSaverUiState.whatsappStatusType)
                        }
                    }
                    statusSaverUiState.isNoWhatsappBusinessStatusFound?.let {
                        if (!statusSaverUiState.isNoWhatsappBusinessStatusFound) {
                            InformationBox(
                                title = "No Status found",
                                message = "You must need to watch status to save."
                            )
                        }
                    }
                    statusSaverUiState.isStatusFound?.let { isStatusFound ->
                        if (isStatusFound) {
                            StatusView(
                                statusSaverUiState.imageStatusList,
                                statusSaverUiState.videoStatusList,
                                onStatusItemClick
                            )
                        }
                    }
                }
            }
            /*TabRow(
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
            when(state){
                0 -> ImageStatusView()
                else -> VideoStatusView()
            }*/

        }
    }

}

@Composable
fun StatusView(
    imageStatusList: ArrayList<Status>,
    videoStatusList: ArrayList<Status>,
    onStatusItemClick: (Status) -> Unit
) {
    val imageStatuses by remember {
        mutableStateOf(imageStatusList)
    }
    val videoStatuses by remember {
        mutableStateOf(videoStatusList)
    }
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf("Images", "Videos")

    Column(modifier = Modifier.fillMaxSize()) {
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
            0 -> ImageStatusView(imageStatuses, onStatusItemClick)
            else -> VideoStatusView(videoStatuses, onStatusItemClick)
        }
    }
}


@Composable
fun NoWhatsappInstalledView(
    whatsappTypeEnum: WhatsappTypeEnum
) {
    val type = stringResource(id = whatsappTypeEnum.title)
    InformationBox(
        title = "No $type found",
        message = "Please make sure $type install and you must watch status to save."
    )
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NoWhatsappInstalledViewPreview() {
    WhatsappStatusSaverToolsTheme {
        NoWhatsappInstalledView(
            WhatsappTypeEnum.WhatsappBusiness
        )
    }
}

@Preview(showSystemUi = true)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatusSaverScreenPreview() {
    WhatsappStatusSaverToolsTheme {
        StatusSaverScreen(
            statusSaverUiState = StatusSaverUiState(),
            onBackPress = {},
            onStatusItemClick = {},
            onShowDownloadClick = {}
        )
    }
}