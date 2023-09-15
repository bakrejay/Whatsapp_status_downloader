package com.ambits.whatsapp.status.saver.tools.ui.screens.status_saver

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.lifecycleScope
import com.ambits.whatsapp.status.saver.tools.data.enums.WhatsappTypeEnum
import com.ambits.whatsapp.status.saver.tools.ui.screens.downloaded_status.DownloadedStatusActivity
import com.ambits.whatsapp.status.saver.tools.ui.screens.status_view.StatusViewActivity
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.KEY_STATUS
import com.ambits.whatsapp.status.saver.tools.utils.AppPref
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.BUSINESS_STATUS_DIRECTORY
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.BUSINESS_STATUS_DIRECTORY_NEW
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.STATUS_DIRECTORY
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.STATUS_DIRECTORY_NEW
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.isWhatsappBusinessInstalled
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.isWhatsappInstalled
import com.gb.wapp.tool.wapptoolsprovider.doIntentForWhatsapp
import com.gb.wapp.tool.wapptoolsprovider.doIntentForWhatsappWeb
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import java.io.File
import java.util.Arrays

open class StatusSaverActivity : ComponentActivity() {

    private val viewModel: StatusSaverViewModel by viewModels()
    private lateinit var appPref: AppPref
    private val imagesList = ArrayList<Status>()
    private val videoList = ArrayList<Status>()

    private val handler = Handler(Looper.getMainLooper())
    private var statusModelList: ArrayList<String> = ArrayList()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data?.data != null) {
                it.data?.data?.also { uri ->
                    if (uri.toString().contains(".Statuses")) {
                        grantUriPermission(
                            packageName,
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        contentResolver.takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        )
                        when (viewModel.statusSaverUiState.value.whatsappStatusType) {
                            WhatsappTypeEnum.WhatsApp -> {
                                appPref.setWhatsappPath(uri.toString())
                                appPref.setWhatsappPermissionGiven()
                                getWhatsappStatuses()
                            }

                            WhatsappTypeEnum.WhatsappBusiness -> {
                                appPref.setWhatsappBusinessPath(uri.toString())
                                appPref.setWhatsappPermissionBusinessGiven()
                                getWhatsappBusinessStatuses()
                            }
                        }
                    } else {
                        AlertDialog.Builder(this@StatusSaverActivity).setTitle("Wrong folder")
                            .setMessage("You have selected wrong folder. Please select .Statuses folder to view Whatsapp Status.")
                            .setPositiveButton("Ok") { _, _ ->
                                initPermission()
                            }.setNegativeButton("Cancel") { _, _ ->
                                finish()
                            }.create().show()
                    }
                    Log.d("TAG", "DAta: $uri")
                }
            } else {
                AlertDialog.Builder(this@StatusSaverActivity).setTitle("No Folder Selected")
                    .setMessage("You have no selected any folder. Please select .Statuses folder to view Whatsapp Status.")
                    .setPositiveButton("Ok") { _, _ ->
                        initPermission()
                    }.setNegativeButton("Cancel") { _, _ ->
                        finish()
                    }.create().show()
            }
        }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPref = AppPref(this)
        initArgs()
        setContent {
            val statusSaverUiState by viewModel.statusSaverUiState.collectAsState()
            WhatsappStatusSaverToolsTheme {
                var isShowPermissionSettingDialog by remember {
                    mutableStateOf(false)
                }

                val multiplePermissionState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    ),
                    onPermissionsResult = { permissionMap ->
                        if (permissionMap.values.contains(false)) {
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)
                                && !shouldShowRequestPermissionRationale(
                                    Manifest.permission.RECORD_AUDIO
                                )
                            ) {
                                isShowPermissionSettingDialog = true
                            }
                        } else {
                            when (viewModel.statusSaverUiState.value.whatsappStatusType) {
                                WhatsappTypeEnum.WhatsApp -> {
                                    appPref.setWhatsappPermissionGiven()
                                    getWhatsappStatuses()
                                }

                                WhatsappTypeEnum.WhatsappBusiness -> {
                                    appPref.setWhatsappPermissionBusinessGiven()
                                    getWhatsappBusinessStatuses()
                                }
                            }
                        }
                    }
                )
                LaunchedEffect(key1 = statusSaverUiState, block = {
                    statusSaverUiState.isNeedToAskStoragePermission?.let { isNeedToAskStoragePermission ->
                        if (isNeedToAskStoragePermission) {
                            multiplePermissionState.launchMultiplePermissionRequest()
                        }
                    }
                })
                if (isShowPermissionSettingDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            isShowPermissionSettingDialog = false
                            viewModel.setNeedToAskStoragePermission(false)
                        },
                        confirmButton = {
                            Button(onClick = {
                                navigateToPermissionSetting()
                                isShowPermissionSettingDialog = false
                                viewModel.setNeedToAskStoragePermission(false)
                            }) {
                                Text(text = "Go to Settings")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                isShowPermissionSettingDialog = false
                                viewModel.setNeedToAskStoragePermission(false)
                                finish()
                            }) { Text(text = "Cancel") }
                        },
                        title = { Text(text = "Permission require") },
                        text = { Text(text = "Please allow permission from setting to continue..") })
                }
                StatusSaverScreen(
                    statusSaverUiState = statusSaverUiState,
                    onBackPress = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onStatusItemClick = { status ->
                        Log.e("TAG", "OnStatusItemClick: ${status.path}")
                        viewStatusFullView(status)
                    },
                    onShowDownloadClick = {
                        navigateToStatusDownloadScreen()
                    }
                )
            }
        }
    }

    private fun navigateToStatusDownloadScreen() {
        startActivity(
            Intent(
                this@StatusSaverActivity,
                DownloadedStatusActivity::class.java
            )
        )
    }

    private fun viewStatusFullView(status: Status) {
        startActivity(
            Intent(
                this@StatusSaverActivity,
                StatusViewActivity::class.java
            ).apply {
                putExtra(KEY_STATUS, status)
            }
        )
    }

    private fun navigateToPermissionSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }


    private fun initPermission() {
        when (viewModel.statusSaverUiState.value.whatsappStatusType) {
            WhatsappTypeEnum.WhatsApp -> checkPermissionForWhatsapp()
            WhatsappTypeEnum.WhatsappBusiness -> checkPermissionForWhatsappBusiness()
        }
    }

    private fun checkPermissionForWhatsappBusiness() {
        if (isWhatsappBusinessInstalled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (appPref.isWhatsappBusinessPermissionGiven()) {
                    getWhatsappBusinessStatuses()
                } else {
                    doIntentForWhatsappWeb(resultLauncher)
                }
            } else {
                checkPermissions()
            }
        } else {
            viewModel.setNoWhatsappBusinessInstalled()
        }
    }

    private fun checkPermissionForWhatsapp() {
        if (isWhatsappInstalled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (appPref.isWhatsappPermissionGiven()) {
                    getWhatsappStatuses()
                } else {
                    doIntentForWhatsapp(resultLauncher)
                }
            } else {
                checkPermissions()
            }
        } else {
            viewModel.setNoWhatsappInstalled()
        }
    }

    private fun checkPermissions() {
        viewModel.setNeedToAskStoragePermission(true)
    }

    private fun getWhatsappStatuses() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val uri = appPref.getWhatsappPath()
            if (uri.isNotEmpty()) {
                execute(uri)
            } else {
                viewModel.setNoWhatsappInstalled()
            }
        } else {
            if (STATUS_DIRECTORY.exists()) {
                execute(STATUS_DIRECTORY)
            } else if (STATUS_DIRECTORY_NEW.exists()) {
                execute(STATUS_DIRECTORY_NEW)
            } else {
                viewModel.setNoWhatsappInstalled()
            }
        }
    }

    private fun getWhatsappBusinessStatuses() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val uri = appPref.getWhatsappBusinessPath()
            if (uri.isNotEmpty()) {
                execute(uri)
            } else {
                viewModel.setNoWhatsappBusinessInstalled()
            }
        } else {
            if (BUSINESS_STATUS_DIRECTORY.exists()) {
                execute(BUSINESS_STATUS_DIRECTORY)
            } else if (BUSINESS_STATUS_DIRECTORY_NEW.exists()) {
                execute(BUSINESS_STATUS_DIRECTORY_NEW)
            } else {
                viewModel.setNoWhatsappBusinessInstalled()
            }
        }
    }

    private fun execute(uri: String) {
        lifecycleScope.launch {
            statusModelList.clear()
            imagesList.clear()
            videoList.clear()
            val allFiles: Array<DocumentFile> = getSdCard(uri)
            for (i in allFiles.indices) {
                if (!allFiles[i].uri.toString().contains(".nomedia")) {
                    statusModelList.add(allFiles[i].uri.toString())
                }
            }
            statusModelList.reverse()
            if (statusModelList.isNotEmpty()) {
                for (file in statusModelList) {
                    val status = Status(file)
                    if (!status.isVideo) {
                        imagesList.add(status)
                    }else{
                        videoList.add(status)
                    }
                }
                handler.post {
                    if (imagesList.size <= 0) {
                        when (viewModel.statusSaverUiState.value.whatsappStatusType) {
                            WhatsappTypeEnum.WhatsApp -> viewModel.setNoWhatsappStatusFound()
                            WhatsappTypeEnum.WhatsappBusiness -> viewModel.setNoWhatsappBusinessStatusFound()
                        }
                    } else {
                        Log.e("TAG", "StatusList: $imagesList")
                        viewModel.setStatusList(imagesList, videoList)
                    }
                }
            } else {
                handler.post {
                    when (viewModel.statusSaverUiState.value.whatsappStatusType) {
                        WhatsappTypeEnum.WhatsApp -> viewModel.setNoWhatsappStatusFound()
                        WhatsappTypeEnum.WhatsappBusiness -> viewModel.setNoWhatsappBusinessStatusFound()
                    }
                }
            }
        }
    }

    private fun getSdCard(uri: String): Array<DocumentFile> {
        val fromTreeUri = DocumentFile.fromTreeUri(
            this@StatusSaverActivity, Uri.parse(uri)
        )
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return emptyArray()
        }
        return fromTreeUri.listFiles()
    }

    private fun execute(wAFolder: File) {
        lifecycleScope.launch {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            imagesList.clear()
            videoList.clear()
            if (!statusFiles.isNullOrEmpty()) {
                Arrays.sort(statusFiles)
                Log.e("TAG", "execute: ${statusFiles.size}")
                for (file in statusFiles) {
                    val status = Status(
                        file,
                        file.name,
                        file.absolutePath
                    )
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        imagesList.add(status)
                    }else if (status.isVideo) {
                        videoList.add(status)
                    }
                }
                handler.post {
                    if (imagesList.size <= 0) {
                        when (viewModel.statusSaverUiState.value.whatsappStatusType) {
                            WhatsappTypeEnum.WhatsApp -> viewModel.setNoWhatsappStatusFound()
                            WhatsappTypeEnum.WhatsappBusiness -> viewModel.setNoWhatsappBusinessStatusFound()
                        }
                    } else {
                        Log.e("TAG", "StatusList: $imagesList")
                        viewModel.setStatusList(imagesList, videoList)
                    }
                }
            } else {
                handler.post {
                    when (viewModel.statusSaverUiState.value.whatsappStatusType) {
                        WhatsappTypeEnum.WhatsApp -> viewModel.setNoWhatsappStatusFound()
                        WhatsappTypeEnum.WhatsappBusiness -> viewModel.setNoWhatsappBusinessStatusFound()
                    }
                }
            }
        }
    }


    @Suppress("DEPRECATION")
    private fun initArgs() {
        intent?.extras?.let { bundle ->
            val whatsappStatusType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(AppConstants.KEY_WHATSAPP_TYPE, WhatsappTypeEnum::class.java)
                    ?: WhatsappTypeEnum.WhatsApp
            } else {
                bundle.getSerializable(AppConstants.KEY_WHATSAPP_TYPE) as WhatsappTypeEnum
            }
            viewModel.updateWhatsappType(whatsappStatusType)
        }
    }

    override fun onResume() {
        super.onResume()
        initPermission()
    }
}