package com.ambits.whatsapp.status.saver.tools.ui.screens.downloaded_status

import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status

data class DownloadedStatusUiState(
    val noVideoStatusFound: Boolean? = null,
    val noImageStatusFound: Boolean? = null,
    val imageStatusList: ArrayList<Status> = ArrayList(),
    val videoStatusList: ArrayList<Status> = ArrayList()
)