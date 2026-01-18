package com.tride.admin.mainui.settingsscreen.viewmodel


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(SettingsUiState())
    val uiState: State<SettingsUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<BaseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun toggleNotifications() {
        _uiState.value = _uiState.value.copy(
            notificationsEnabled = !_uiState.value.notificationsEnabled
        )
    }

    fun toggleDarkMode() {
        _uiState.value = _uiState.value.copy(
            darkModeEnabled = !_uiState.value.darkModeEnabled
        )
        // In a real app, you would apply the theme change here
    }

    fun onLogoutClicked() {
        viewModelScope.launch {
            _eventFlow.emit(BaseViewModel.UiEvent.Navigate(Screen.AuthScreen.route))
        }
    }
}