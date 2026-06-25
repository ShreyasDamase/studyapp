package com.example.studyapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onChannelPress: () -> Unit,
    onCoroutinePress: () -> Unit,
    onStateFlowPress: () -> Unit,
    onSharedFlowPress: () -> Unit,
    onFlowPress: () -> Unit,
    onAnimationPress: () -> Unit,
    onAnimationTwoPress: () -> Unit
) {
    LaunchedEffect(Unit) {
        homeViewModel.navigationEvent.collect {
            when (it) {
                NavigationEvent.NavigateToSharedFlowScreen -> {
                    onSharedFlowPress()
                }

                NavigationEvent.NavigateToFlowScreen -> {
                    onFlowPress()
                }

                NavigationEvent.NavigateToAnimationScreen -> {
                    onAnimationPress()
                }

                NavigationEvent.NavigateToAnimationTwoScreen -> {
                    onAnimationTwoPress()
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = onCoroutinePress
            ) { Text("On Coroutine Press") }

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = onChannelPress
            ) { Text("On Channel Press") }

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = onStateFlowPress
            ) { Text("On StateFlow Press") }

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { homeViewModel.navigateToSharedFlowScreen() }
            ) { Text("On SharedFlow Press") }

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { homeViewModel.navigateToFlowScreen() }
            ) { Text("On Flow Press") }

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { homeViewModel.navigateToAnimationScreen() }
            ) { Text("On Animation Press") }

            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = { homeViewModel.navigateToAnimationTwoScreen() }
            ) { Text("On Animation Two Press") }
        }
    }
}