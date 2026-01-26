package com.tride.admin.mainui.adminscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor() : BaseViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun onLogoutClicked() {
        viewModelScope.launch {
            // 1. Sign out from Firebase
            auth.signOut()

            // 2. Navigate back to Auth Screen
            sendEvent(UiEvent.Navigate(Screen.AuthScreen.route))
        }
    }

    fun onManageDriversClicked() {
        navigate(Screen.ManageDriversScreen.route)
    }

    fun onManageRidersClicked() {
        navigate(Screen.ManageRidersScreen.route)
    }

    fun onViewRidesClicked() {
        navigate(Screen.RideHistoryScreen.route)
    }

    fun onSettingsClicked() {
        navigate(Screen.SettingScreen.route)
    }

    fun onNotificationsClicked() {
        navigate(Screen.NotificationScreen.route)
    }

    // ADDED THIS FUNCTION
    fun onRevenueClicked() {
        navigate(Screen.EarningsScreen.route)
    }

    private fun navigate(route: String) {
        sendEvent(UiEvent.Navigate(route))
    }
}