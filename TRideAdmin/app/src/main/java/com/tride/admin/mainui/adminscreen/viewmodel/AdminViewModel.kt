package com.tride.admin.mainui.adminscreen.viewmodel

import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor() : BaseViewModel() {

    // Manage Drivers
    fun onManageDriversClicked() {
        sendEvent(UiEvent.Navigate(Screen.ManageDriversScreen.route))
    }

    // Manage Riders
    fun onManageRidersClicked() {
        sendEvent(UiEvent.ShowSnackbar("Manage Riders feature coming soon!"))
    }

    // View Rides
    fun onViewRidesClicked() {
        sendEvent(UiEvent.ShowSnackbar("View Rides feature coming soon!"))
    }

    // Logout
    fun onLogoutClicked() {
        sendEvent(UiEvent.Navigate(Screen.AuthScreen.route))
    }
}