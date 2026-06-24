package com.example.studyapp.ui.screens.stateFlow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val message: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}


class StateFlowViewModel() : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUiState>(
        LoginUiState.Idle
    )

    val loginUiState = _loginUiState.asStateFlow()
    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()

    fun increaseCount() {
        _count.value += 1
    }

    fun incrementEmit() {
        viewModelScope.launch(Dispatchers.IO) {

            _count.emit(_count.value + 1)

        }
    }

    fun onLogin(
        email: String,
        password: String
    ) {


        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            delay(5000)

            if (email == "shreyas@gmail.com" && password == "123") {
                _loginUiState.value = LoginUiState.Success("Logged in successfully")
            } else {
                _loginUiState.value = LoginUiState.Error("wrong email or pass")


            }
        }
    }
}