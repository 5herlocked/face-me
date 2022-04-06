package com.faceme.faceme.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.exoplayer2.SimpleExoPlayer
import javax.sql.DataSource

@Composable
fun VideoPlayer() {
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        SimpleExoPlayer.Builder(context).build().apply {
            val dataSourceFactory: DataSource.
        }
    }
}