package com.tride.admin.mainui.editprofilescreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tride.admin.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileState(
    val name: String = "Administrator",
    val email: String = "admin@tride.com",
    val phone: String = "+91 9876543210",
    val isLoading: Boolean = false
)

@HiltViewModel
class EditProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(EditProfileState())
    val uiState: State<EditProfileState> = _uiState

    private val _eventFlow = MutableSharedFlow<BaseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onPhoneChange(newValue: String) {
        _uiState.value = _uiState.value.copy(phone = newValue)
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Simulate API call
            delay(1500)
            _uiState.value = _uiState.value.copy(isLoading = false)
            _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar("Profile updated successfully"))
            delay(500)
            _eventFlow.emit(BaseViewModel.UiEvent.NavigateBack)
        }
    }
}