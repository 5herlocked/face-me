package com.faceme.faceme.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

@Composable
fun VideoPlayer() {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().also { exoPlayer ->
            /* TODO: Finish implementing this */
            val mediaItem = MediaItem.fromUri("")

            exoPlayer.setMediaItem(mediaItem)
        }
    }
}