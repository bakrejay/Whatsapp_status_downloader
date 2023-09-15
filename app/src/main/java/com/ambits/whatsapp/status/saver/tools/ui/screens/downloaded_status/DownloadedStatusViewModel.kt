package com.ambits.whatsapp.status.saver.tools.ui.screens.downloaded_status

import androidx.lifecycle.ViewModel
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DownloadedStatusViewModel : ViewModel() {

    private var _downloadedStatusUiState: MutableStateFlow<DownloadedStatusUiState> =
        MutableStateFlow(DownloadedStatusUiState())
    val downloadedStatusUiState = _downloadedStatusUiState.asStateFlow()

    fun setNoImageStatusFound() {
        _downloadedStatusUiState.value = _downloadedStatusUiState.value.copy(
            noImageStatusFound = false,
            imageStatusList = ArrayList()
        )
    }

    fun setNoVideoStatusFound() {
        _downloadedStatusUiState.value = _downloadedStatusUiState.value.copy(
            noVideoStatusFound = false,
            videoStatusList = ArrayList()
        )
    }

    fun addVideoStatus(videoList: ArrayList<Status>) {
        _downloadedStatusUiState.value = _downloadedStatusUiState.value.copy(
            noVideoStatusFound = true,
            videoStatusList = videoList
        )
    }

    fun addImageStatus(imageList: ArrayList<Status>) {
        _downloadedStatusUiState.value = _downloadedStatusUiState.value.copy(
            noImageStatusFound = true,
            imageStatusList = imageList
        )
    }

}