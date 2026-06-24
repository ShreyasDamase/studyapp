package com.example.studyapp.ui.screens.sharedFlow


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.studyapp.ui.screens.stateFlow.LoginUiState
import com.example.studyapp.ui.screens.stateFlow.StateFlowViewModel
import kotlin.contracts.contract


@Composable
fun SharedFlowScreen(
    viewModel: SharedFlowViewModel,
    onBackPress: () -> Unit,
    modifier: Modifier.Companion
) {
    val context = LocalContext.current
    val snackBar = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    //toast
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.ShowSnackBar -> {
                    //snack bar
                    snackBar.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Column() {
        Button(onClick = onBackPress) { Text("back press") }
        Text("SharedFlow Screen")

        Button(onClick = { viewModel.showToast() }) { Text("Show Toast") }
        Button(onClick = { viewModel.showSnackbar() }) { Text("Show Snackbar") }
    }
}