package com.example.studyapp.ui.screens.channel

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun ChannelScreen(
    modifier: Modifier.Companion,
    onBackPress: () -> Unit,
    viewModel: ChannelViewModel
) {

    val uiText by viewModel.uiText.collectAsStateWithLifecycle()

    Column() {
        Text(uiText)
        Button(onClick = onBackPress) { Text("Back") }
        Button(onClick = { viewModel.onChannelClick2() }) { Text("Onclick channel") }
    }

}