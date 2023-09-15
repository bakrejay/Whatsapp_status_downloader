package com.ambits.whatsapp.status.saver.tools.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusItemVideoView(videoStatus: Status, onStatusItemClick: (Status) -> Unit) {
    Card(
        onClick = {
            onStatusItemClick(videoStatus)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .build()
        val painter = rememberAsyncImagePainter(
            model = videoStatus.path,
            imageLoader = imageLoader
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
    }
}