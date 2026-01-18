package com.tride.admin.core.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    // ✅ RESTORED: These were missing, causing the 'Unresolved reference: isLoading' error
    protected val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    protected val _apiError = mutableStateOf<String?>(null)
    val apiError: State<String?> = _apiError

    // Event Flow for Navigation and Snackbars
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateBack : UiEvent() // ✅ NEEDED: For the 'NavigateBack' error
    }
}