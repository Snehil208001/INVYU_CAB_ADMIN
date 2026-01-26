package com.tride.admin.core.navigations

sealed class Screen(val route: String) {
    object AuthScreen : Screen("auth_screen")
    // OtpScreen removed as per request

    object AdminScreen : Screen("admin_screen")
    object ManageDriversScreen : Screen("manage_drivers_screen")
    object ManageRidersScreen : Screen("manage_riders_screen")
    object RideHistoryScreen : Screen("ride_history_screen")
    object SettingScreen : Screen("setting_screen")
    object RideDetailScreen : Screen("ride_detail_screen/{rideId}") {
        fun createRoute(rideId: String) = "ride_detail_screen/$rideId"
    }
    object DriverDetailScreen : Screen("driver_detail_screen/{driverId}") {
        fun createRoute(driverId: String) = "driver_detail_screen/$driverId"
    }
    object RiderDetailScreen : Screen("rider_detail_screen/{riderId}") {
        fun createRoute(riderId: String) = "rider_detail_screen/$riderId"
    }
    object NotificationScreen : Screen("notification_screen")
    object EarningsScreen : Screen("earnings_screen")
    object EditProfileScreen : Screen("edit_profile_screen")
}