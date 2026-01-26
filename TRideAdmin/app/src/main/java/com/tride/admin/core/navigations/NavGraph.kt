package com.tride.admin.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tride.admin.mainui.adminscreen.ui.AdminScreen
import com.tride.admin.mainui.driverdetailscreen.ui.DriverDetailScreen
import com.tride.admin.mainui.earningsscreen.ui.EarningsScreen
import com.tride.admin.mainui.editprofilescreen.ui.EditProfileScreen
import com.tride.admin.mainui.managedriversscreen.ui.ManageDriversScreen
import com.tride.admin.mainui.manageridersscreen.ui.ManageRidersScreen
import com.tride.admin.mainui.notificationscreen.ui.NotificationScreen
import com.tride.admin.mainui.ridedetailscreen.ui.RideDetailScreen
import com.tride.admin.mainui.riderdetailscreen.ui.RiderDetailScreen
import com.tride.admin.mainui.ridehistoryscreen.ui.RideHistoryScreen
import com.tride.admin.mainui.settingsscreen.ui.SettingScreen
import com.tride.admin.mainui.authscreen.ui.AuthScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String // Received from MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = startDestination // Use the dynamic start destination
    ) {
        // 1. Auth
        composable(Screen.AuthScreen.route) {
            AuthScreen(navController = navController)
        }

        // 2. Admin Dashboard (Home)
        composable(Screen.AdminScreen.route) {
            AdminScreen(navController = navController)
        }

        // 3. Manage Drivers
        composable(Screen.ManageDriversScreen.route) {
            ManageDriversScreen(navController = navController)
        }

        // 4. Manage Riders
        composable(Screen.ManageRidersScreen.route) {
            ManageRidersScreen(navController = navController)
        }

        // 5. Ride History
        composable(Screen.RideHistoryScreen.route) {
            RideHistoryScreen(navController = navController)
        }

        // 6. Settings
        composable(Screen.SettingScreen.route) {
            SettingScreen(navController = navController)
        }

        // 7. Notifications
        composable(Screen.NotificationScreen.route) {
            NotificationScreen(navController = navController)
        }

        // 8. Earnings / Revenue
        composable(Screen.EarningsScreen.route) {
            EarningsScreen(navController = navController)
        }

        // 9. Edit Profile
        composable(Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }

        // --- Detail Screens with Arguments ---

        // 10. Driver Detail
        composable(
            route = Screen.DriverDetailScreen.route + "/{driverId}",
            arguments = listOf(navArgument("driverId") { type = NavType.StringType })
        ) { backStackEntry ->
            val driverId = backStackEntry.arguments?.getString("driverId") ?: ""
            DriverDetailScreen(navController = navController, driverId = driverId)
        }

        // 11. Rider Detail
        composable(
            route = Screen.RiderDetailScreen.route + "/{riderId}",
            arguments = listOf(navArgument("riderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val riderId = backStackEntry.arguments?.getString("riderId") ?: ""
            RiderDetailScreen(navController = navController, riderId = riderId)
        }

        // 12. Ride Detail
        composable(
            route = Screen.RideDetailScreen.route + "/{rideId}",
            arguments = listOf(navArgument("rideId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId") ?: ""
            RideDetailScreen(navController = navController, rideId = rideId)
        }
    }
}