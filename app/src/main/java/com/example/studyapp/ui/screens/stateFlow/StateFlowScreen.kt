package com.example.studyapp.ui.screens.stateFlow


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope


@Composable
fun StateFlowScreen(
    viewModel: StateFlowViewModel,
    onBackPress: () -> Unit,
    modifier: Modifier.Companion
) {

    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    Column() {

        Button(onClick = { onBackPress() }) {
            Text("Back")
        }

        Spacer(modifier = modifier.height(10.dp))

        Button(onClick = {
            viewModel.onLogin(
                email = "shreyasd@gmail.com",
                password = "1234"
            )
        }) { Text("log in ") }

        when (val state = loginUiState) {
            LoginUiState.Idle -> Text("successfully login ")
            LoginUiState.Loading -> CircularProgressIndicator()
            is LoginUiState.Success -> Text(state.message)
            is LoginUiState.Error -> Text(state.message)
        }


    }
}