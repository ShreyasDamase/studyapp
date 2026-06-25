package com.example.studyapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface NavigationEvent {
    data object NavigateToSharedFlowScreen : NavigationEvent
    data object NavigateToFlowScreen : NavigationEvent
    data object NavigateToAnimationScreen : NavigationEvent
    data object NavigateToAnimationTwoScreen : NavigationEvent
}

class HomeViewModel() : ViewModel() {
    private val _navigationEvent =
        MutableSharedFlow<NavigationEvent>(replay = 0, extraBufferCapacity = 1)

    val navigationEvent = _navigationEvent.asSharedFlow()

    fun navigateToSharedFlowScreen() {

        viewModelScope.launch {
            val success = _navigationEvent.tryEmit(
                NavigationEvent.NavigateToSharedFlowScreen
            )
            Log.d("SharedFlow", "success = $success")

        }


    }

    fun navigateToFlowScreen() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToFlowScreen)
        }
    }

    fun navigateToAnimationScreen() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToAnimationScreen)
        }
    }

    fun navigateToAnimationTwoScreen() {
        viewModelScope.launch {
            _navigationEvent.emit(NavigationEvent.NavigateToAnimationTwoScreen)
        }
    }


}