package com.tride.admin.mainui.adminscreen.ui

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tride.admin.core.navigations.Screen
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    // --- State Variables ---
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    // Forgot Password States
    val showResetDialog = mutableStateOf(false)
    val resetEmail = mutableStateOf("")

    // Event Flow
    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    // --- Events ---

    fun onEmailChange(newEmail: String) {
        // Prevent newlines if pasted
        email.value = newEmail.replace("\n", "").trim()
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword.trim()
    }

    fun onResetEmailChange(newEmail: String) {
        resetEmail.value = newEmail.trim()
    }

    fun toggleResetDialog(show: Boolean) {
        showResetDialog.value = show
    }

    fun onSendResetLinkClicked() {
        toggleResetDialog(false)
        viewModelScope.launch {
            _eventFlow.send(UiEvent.ShowSnackbar("Reset link sent (Simulation)"))
        }
    }

    fun onLoginClicked() {
        // Aggressively clean the input
        val mail = email.value.trim().replace("\\s".toRegex(), "")
        val pass = password.value.trim()

        // 1. Validation Checks
        if (mail.isBlank() || pass.isBlank()) {
            viewModelScope.launch {
                _eventFlow.send(UiEvent.ShowSnackbar("Please fill in all fields"))
            }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            viewModelScope.launch {
                _eventFlow.send(UiEvent.ShowSnackbar("Invalid Email Address format"))
            }
            return
        }

        // 2. Attempt Login
        viewModelScope.launch {
            isLoading.value = true

            val result = repository.loginAdmin(mail, pass)

            when (result) {
                is Resource.Success -> {
                    isLoading.value = false
                    _eventFlow.send(UiEvent.ShowSnackbar("Login Successful"))

                    // --- FIX IS HERE ---
                    // Use the Screen object instead of a hardcoded string
                    _eventFlow.send(UiEvent.Navigate(Screen.AdminScreen.route))
                }
                is Resource.Error -> {
                    isLoading.value = false
                    _eventFlow.send(UiEvent.ShowSnackbar(result.message ?: "Login Failed"))
                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }

    // Simple Sealed Class for UI Events
    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class Navigate(val route: String) : UiEvent()
        object NavigateBack : UiEvent()
    }
}