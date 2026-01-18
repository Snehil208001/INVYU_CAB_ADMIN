package com.tride.admin.mainui.adminscreen.viewmodel

import androidx.lifecycle.viewModelScope
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    // This helper function now works because _eventFlow is protected
    private fun navigate(route: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.Navigate(route))
        }
    }
}