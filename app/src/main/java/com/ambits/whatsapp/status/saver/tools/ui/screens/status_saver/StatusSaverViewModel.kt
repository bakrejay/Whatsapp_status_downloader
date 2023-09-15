package com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver

import androidx.lifecycle.ViewModel
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StatusSaverViewModel : ViewModel() {
    private var _statusSaverUiState: MutableStateFlow<StatusSaverUiState> =
        MutableStateFlow(StatusSaverUiState())
    val statusSaverUiState = _statusSaverUiState.asStateFlow()

    fun updateWhatsappType(whatsappStatusType: WhatsappTypeEnum) {
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            whatsappStatusType = whatsappStatusType
        )
    }

    fun setNoWhatsappInstalled() {
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            isWhatsappInstalled = false
        )
    }

    fun setNoWhatsappBusinessInstalled() {
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            isWhatsappBusinessInstalled = false
        )
    }

    fun setNoWhatsappStatusFound() {
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            isWhatsappInstalled = true,
            isNoWhatsappStatusFound = false,
            isStatusFound = null,
        )
    }

    fun setNoWhatsappBusinessStatusFound() {
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            isWhatsappBusinessInstalled = true,
            isNoWhatsappBusinessStatusFound = false,
            isStatusFound = null,

        )
    }

    fun setStatusList(statuses: ArrayList<Status>, videoList: ArrayList<Status>){
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            isWhatsappInstalled = null,
            isNoWhatsappStatusFound = null,
            isWhatsappBusinessInstalled = null,
            isNoWhatsappBusinessStatusFound = null,
            isStatusFound = true,
            imageStatusList = statuses,
            videoStatusList = videoList
        )
    }

    fun setNeedToAskStoragePermission(isNeedToAsk: Boolean) {
        _statusSaverUiState.value = _statusSaverUiState.value.copy(
            isNeedToAskStoragePermission = isNeedToAsk
        )
    }
}