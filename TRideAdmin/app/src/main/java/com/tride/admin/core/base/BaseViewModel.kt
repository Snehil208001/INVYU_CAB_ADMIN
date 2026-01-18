package com.tride.admin.core.base


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    protected val _apiError = mutableStateOf<String?>(null)
    val apiError: State<String?> = _apiError

    private val _eventChannel = Channel<UiEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }

    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}