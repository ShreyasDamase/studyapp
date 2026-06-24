package com.example.studyapp.ui.screens.sharedFlow

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

sealed interface UiEvent {
    data class ShowToast(val message: String) : UiEvent
    data class ShowSnackBar(val message: String) : UiEvent


}

class SharedFlowViewModel() : ViewModel() {
    private val _uiEvent = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val uiEvents = _uiEvent.asSharedFlow()


    fun showToast() {
        viewModelScope.launch {
            _uiEvent.emit(
                UiEvent.ShowToast("hi hello")
            )
        }
    }

    fun showSnackbar() {

        _uiEvent.tryEmit(
            UiEvent.ShowToast("hi sh")
        )

    }
}