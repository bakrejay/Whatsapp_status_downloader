package com.ambits.whatsapp.status.saver.tools.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ambits.whatsapp.status.saver.whatsapptoolprovider.data.Status
import java.util.ArrayList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusItemImageView(imageStatus: Status, onStatusItemClick: (Status) -> Unit) {
    Card(
        onClick = {
            onStatusItemClick(imageStatus)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(
                    model = imageStatus.path,
                ),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )
        }
    }
}