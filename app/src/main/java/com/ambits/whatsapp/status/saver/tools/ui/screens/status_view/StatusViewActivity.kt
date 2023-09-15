package com.ambits.whatsapp.status.saver.tools.ui.screens.status_view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.palette.graphics.Palette
import coil.compose.rememberAsyncImagePainter
import com.ambits.whatsapp.status.saver.tools.R
import com.ambits.whatsapp.status.saver.tools.ui.theme.WhatsappStatusSaverToolsTheme
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.ACTION_RELOAD_SAVED_STATUS
import com.ambits.whatsapp.status.saver.tools.utils.AppConstants.APP_FILE_PROVIDER
import com.ambits.whatsapp.status.saver.tools.utils.getBitmapFromFilePath
import com.ambits.whatsapp.status.saver.tools.utils.setFullScreen
import com.ambits.whatsapp.status.saver.tools.utils.shareFile
import com.ambits.whatsapp.status.saver.tools.utils.shareImage
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.copyFile
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.helpers.DirectoryUtils.saveFile
import java.io.File


class StatusViewActivity : ComponentActivity() {
    private var status: Status? = null
    private var isFromDownloads: Boolean = false
    private var exoPlayer: ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs()
        exoPlayer = ExoPlayer.Builder(this@StatusViewActivity).build()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContent {
            status?.let { status ->
                StatusViewScreen(
                    status = status,
                    exoPlayer = exoPlayer,
                    context = this@StatusViewActivity,
                    isFromDownloads = isFromDownloads,
                    onSaveStatusClick = { savedStatus: Status -> saveStatus(savedStatus) },
                    onShareStatusClick = { sharedStatus: Status -> shareStatus(sharedStatus) },
                    onBackPress = { onBackPressedDispatcher.onBackPressed() },
                    onStatusDeleteClick = { sharedStatus: Status -> deleteStatus(sharedStatus) }
                )
            }

        }
        setFullScreen()
    }

    private fun shareStatus(status: Status) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            shareFile(status)
        } else {
            shareImage(status)
        }
    }

    private fun deleteStatus(status: Status) {
        if (status.file.delete()) {
            Toast.makeText(
                this@StatusViewActivity,
                "Deleted Successfully",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this@StatusViewActivity,
                "Unable to Delete File",
                Toast.LENGTH_SHORT
            ).show()
        }
        sendBroadcast(Intent(ACTION_RELOAD_SAVED_STATUS))
        onBackPressedDispatcher.onBackPressed()
    }

    private fun saveStatus(status: Status) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            saveFile(status, this@StatusViewActivity, R.string.app_name)
        } else {
            copyFile(status, this@StatusViewActivity, R.string.app_name)
        }
    }

    @Suppress("DEPRECATION")
    private fun initArgs() {
        intent?.extras?.let { bundle ->
            status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(AppConstants.KEY_STATUS, Status::class.java)
            } else {
                bundle.getSerializable(AppConstants.KEY_STATUS) as Status?
            }
            isFromDownloads = bundle.getBoolean(AppConstants.KEY_IS_FROM_DOWNLOADS, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.let {
            it.pause()
            it.clearMediaItems()
        }
        exoPlayer = null
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        exoPlayer?.play()
    }
}

@Composable
fun StatusViewScreen(
    status: Status,
    exoPlayer: ExoPlayer?,
    context: Context,
    isFromDownloads: Boolean,
    onBackPress: () -> Unit,
    onSaveStatusClick: (Status) -> Unit,
    onShareStatusClick: (Status) -> Unit,
    onStatusDeleteClick: (Status) -> Unit
) {
    WhatsappStatusSaverToolsTheme {
        var dominantColor by remember {
            mutableIntStateOf(R.color.black)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            if (!status.isVideo) {
                context.getBitmapFromFilePath(status.path)?.let { bitmap ->
                    Palette.Builder(bitmap).generate {
                        it?.let { palette ->
                            dominantColor = palette.getDominantColor(
                                ContextCompat.getColor(
                                    context, R.color.black
                                )
                            )
                        }
                    }
                }
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(dominantColor)),
                    painter = rememberAsyncImagePainter(
                        model = status.path,
                    ),
                    contentDescription = ""
                )
            } else {
                AndroidView(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black),
                    factory = { context ->
                        PlayerView(context).also {
                            it.player = exoPlayer?.apply {
                                repeatMode = Player.REPEAT_MODE_ONE
                                playWhenReady = true
                                setMediaItem(MediaItem.fromUri(status.path))
                                prepare()
                            }
                            it.useController = false
                        }
                    })
            }
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    FilledTonalIconButton(
                        onClick = { onBackPress() },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            Color(
                                ContextCompat.getColor(context, R.color.white_80)
                            )
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Share"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                        .background(
                            Color(
                                ContextCompat.getColor(context, R.color.white_50)
                            )
                        )
                        .padding(16.dp)
                ) {
                    if (isFromDownloads) {
                        Button(
                            onClick = {
                                onStatusDeleteClick(status)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                            ),
                            modifier = Modifier
                                .weight(1F)
                        ) {
                            Text(text = "Delete")
                        }
                    } else {
                        Button(onClick = {
                            onSaveStatusClick(status)
                        }, modifier = Modifier.weight(1F)) {
                            Text(text = "Save")
                        }
                    }

                    FilledTonalIconButton(onClick = {
                        onShareStatusClick(status)
                    }) {
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = "Share")
                    }
                }
            }
        }
    }
}