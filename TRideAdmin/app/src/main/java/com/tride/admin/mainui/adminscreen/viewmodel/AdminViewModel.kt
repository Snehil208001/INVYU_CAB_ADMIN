package com.tride.admin.mainui.adminscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.navigations.Screen
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// State to hold the dashboard data
data class DashboardState(
    val driverCount: String = "0",
    val userCount: String = "0",
    val totalRevenue: String = "₹0.00",
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false // Added for pull-to-refresh
)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: DashboardRepository
) : BaseViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _dashboardState = mutableStateOf(DashboardState())
    val dashboardState: State<DashboardState> = _dashboardState

    init {
        fetchDashboardData()
    }

    // Public function triggered by the UI Pull-to-Refresh
    fun onRefresh() {
        fetchDashboardData(isSwipeRefresh = true)
    }

    private fun fetchDashboardData(isSwipeRefresh: Boolean = false) {
        val currentUserEmail = auth.currentUser?.email

        if (currentUserEmail != null) {
            viewModelScope.launch {
                // Update specific state based on the trigger source
                _dashboardState.value = if (isSwipeRefresh) {
                    _dashboardState.value.copy(isRefreshing = true)
                } else {
                    _dashboardState.value.copy(isLoading = true)
                }

                // Use the logged-in email
                val result = repository.getDashboardDetails(currentUserEmail)

                when (result) {
                    is Resource.Success -> {
                        val data = result.data
                        if (data != null) {
                            // Extract counts based on role
                            val driverStats = data.userStatistics.find { it.userRole == "driver" }
                            val riderStats = data.userStatistics.find { it.userRole == "rider" }

                            _dashboardState.value = _dashboardState.value.copy(
                                driverCount = driverStats?.userCount?.toString() ?: "0",
                                userCount = riderStats?.userCount?.toString() ?: "0",
                                totalRevenue = "₹${data.totalRevenue}",
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    }
                    is Resource.Error -> {
                        _dashboardState.value = _dashboardState.value.copy(
                            isLoading = false,
                            isRefreshing = false
                        )
                        sendEvent(UiEvent.ShowSnackbar(result.message ?: "Failed to load data"))
                    }
                    is Resource.Loading -> {
                        // Handled by local state
                    }
                }
            }
        } else {
            _dashboardState.value = _dashboardState.value.copy(isRefreshing = false, isLoading = false)
            sendEvent(UiEvent.ShowSnackbar("User email not found"))
        }
    }

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

    fun onRevenueClicked() {
        navigate(Screen.EarningsScreen.route)
    }

    private fun navigate(route: String) {
        sendEvent(UiEvent.Navigate(route))
    }
}