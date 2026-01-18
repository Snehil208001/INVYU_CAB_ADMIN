package com.tride.admin.core.navigations

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    object OtpScreen : Screen("otp_screen/{mobileNumber}") {
        fun createRoute(mobileNumber: String) = "otp_screen/$mobileNumber"
    }
    object AdminScreen : Screen("admin_screen")
    object ManageDriversScreen : Screen("manage_drivers_screen")
    object ManageRidersScreen : Screen("manage_riders_screen") // Optional: For User Base
    object RideHistoryScreen : Screen("ride_history_screen")   // New: For Ride History
}