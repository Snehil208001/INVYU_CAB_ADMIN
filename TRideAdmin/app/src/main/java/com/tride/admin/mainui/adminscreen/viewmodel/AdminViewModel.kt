package com.tride.admin.mainui.adminscreen.viewmodel

import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor() : BaseViewModel() {

    fun onLogoutClicked() {
        navigate(Screen.AuthScreen.route)
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

    private fun navigate(route: String) {
        // Use sendEvent from BaseViewModel instead of accessing private _eventFlow
        sendEvent(UiEvent.Navigate(route))
    }
}