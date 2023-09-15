package com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver

import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.util.ArrayList

data class StatusSaverUiState(
    val isWhatsappInstalled: Boolean? = null,
    val isWhatsappBusinessInstalled: Boolean? = null,
    val isNoWhatsappStatusFound: Boolean? = null,
    val isNoWhatsappBusinessStatusFound: Boolean? = null,
    val isNeedToAskStoragePermission: Boolean? = null,
    val whatsappStatusType: WhatsappTypeEnum = WhatsappTypeEnum.WhatsApp,
    val isStatusFound: Boolean? = null,
    val imageStatusList: ArrayList<Status> = ArrayList<Status>(),
    val videoStatusList: ArrayList<Status> = ArrayList<Status>()
)
