package com.tride.admin.mainui.otpscreen.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor() : BaseViewModel() {

    val otpValue = mutableStateOf("")

    fun onOtpChange(newValue: String) {
        if (newValue.all { it.isDigit() }) {
            otpValue.value = newValue
        }
    }

    fun onVerifyClicked() {
        if (otpValue.value.length == 6) {
            verifyOtp()
        } else {
            sendEvent(UiEvent.ShowSnackbar("Please enter a valid 6-digit OTP"))
        }
    }

    private fun verifyOtp() {
        _isLoading.value = true
        viewModelScope.launch {
            // Simulate Network Call
            delay(1500)
            _isLoading.value = false

            // Success: Navigate to Admin Dashboard
            sendEvent(UiEvent.Navigate(Screen.AdminScreen.route))
        }
    }
}