package com.ambits.whatsapp.status.saver.tools.ui.screens.downloaded_status

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.ui.screens.status_view.StatusViewActivity
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.io.File
import java.util.Arrays

class DownloadedStatusActivity : ComponentActivity()  {

    private val imagesList = ArrayList<Status>()
    private val videoList = ArrayList<Status>()
    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: DownloadedStatusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(
            reloadStatusBroadCastReceiver,
            IntentFilter(AppConstants.ACTION_RELOAD_SAVED_STATUS)
        )
        try{
            setContent {
                val downloadedStatusUiState by viewModel.downloadedStatusUiState.collectAsState()
                WhatsappStatusSaverToolsTheme {
                    DownloadedStatusScreen(
                        downloadedStatusUiState = downloadedStatusUiState,
                        onBackPress = {
                            onBackPressedDispatcher.onBackPressed()
                        },
                        onStatusItemClick = { status: Status ->
                            viewStatusFullView(status)
                        }
                    )
                }
            }
        }catch (_: Exception){

        }catch (_: java.lang.IndexOutOfBoundsException){}
        getSavedImageStatus()
        getSavedVideoStatus()
    }

    private fun viewStatusFullView(status: Status) {
        startActivity(
            Intent(
                this@DownloadedStatusActivity,
                StatusViewActivity::class.java
            ).apply {
                putExtra(AppConstants.KEY_STATUS, status)
                putExtra(AppConstants.KEY_IS_FROM_DOWNLOADS, true)
            }
        )
    }

    private fun getSavedImageStatus() {
        val appDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "Download" + File.separator + resources.getString(
                R.string.app_name
            ) + "/Images"
        )

        if (appDir.exists()) {
            Thread {
                val savedFiles: Array<File>? = appDir.listFiles()
                imagesList.clear()
                if (savedFiles != null && savedFiles.isNotEmpty()) {
                    Arrays.sort(savedFiles)
                    for (file in savedFiles) {
                        val status = Status(
                            file,
                            file.name,
                            file.absolutePath
                        )
                        if (!status.isVideo && status.title.endsWith(".jpg")) {
                            imagesList.add(status)
                        }
                    }
                    handler.post {
                        if (imagesList.size <= 0) {
                            viewModel.setNoImageStatusFound()
//                            showNoSavedStatusFoundView()
                        } else {
                            viewModel.addImageStatus(imagesList)
//                            updateStatusLayout()
                        }
                    }
                } else {
                    handler.post {
                        viewModel.setNoImageStatusFound()
                    }
                }
            }.start()
        } else {
            viewModel.setNoImageStatusFound()
        }
    }

    private fun getSavedVideoStatus() {
        val appDir = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "Download" + File.separator + resources.getString(
                R.string.app_name
            ) + "/Videos"
        )

        if (appDir.exists()) {
            Thread {
                val savedFiles: Array<File>? = appDir.listFiles()
                videoList.clear()
                if (savedFiles != null && savedFiles.isNotEmpty()) {
                    Arrays.sort(savedFiles)
                    for (file in savedFiles) {
                        val status = Status(
                            file,
                            file.name,
                            file.absolutePath
                        )
                        if (status.isVideo) {
                            videoList.add(status)
                        }
                    }
                    handler.post {
                        if (videoList.size <= 0) {
                            viewModel.setNoVideoStatusFound()
//                            showNoSavedStatusFoundView()
                        } else {
                            viewModel.addVideoStatus(videoList)
//                            updateStatusLayout()
                        }
                    }
                } else {
                    handler.post {
                        viewModel.setNoVideoStatusFound()
                    }
                }
            }.start()
        } else {
            viewModel.setNoVideoStatusFound()
        }
    }

    private val reloadStatusBroadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            videoList.clear()
            imagesList.clear()
            viewModel.addImageStatus(ArrayList())
            viewModel.addVideoStatus(ArrayList())
            getSavedImageStatus()
            getSavedVideoStatus()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.addImageStatus(ArrayList())
        viewModel.addVideoStatus(ArrayList())
    }

    override fun onResume() {
        super.onResume()
        viewModel.addImageStatus(imagesList)
        viewModel.addVideoStatus(videoList)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reloadStatusBroadCastReceiver)
    }
}