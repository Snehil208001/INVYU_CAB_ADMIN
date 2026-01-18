package com.tride.admin.mainui.authscreen.viewmodel


import androidx.compose.runtime.mutableStateOf
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : BaseViewModel() {

    val mobileNumber = mutableStateOf("")

    fun onMobileNumberChange(number: String) {
        if (number.length <= 10 && number.all { it.isDigit() }) {
            mobileNumber.value = number
        }
    }

    fun onSignInClicked() {
        if (mobileNumber.value.length == 10) {
            // ✅ Navigate to OTP Screen passing the number
            sendEvent(UiEvent.Navigate(Screen.OtpScreen.createRoute(mobileNumber.value)))
        } else {
            sendEvent(UiEvent.ShowSnackbar("Please enter a valid 10-digit number"))
        }
    }
}