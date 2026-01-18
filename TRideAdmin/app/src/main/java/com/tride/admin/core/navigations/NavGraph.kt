package com.tride.admin.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tride.admin.mainui.adminscreen.ui.AdminScreen
import com.tride.admin.mainui.authscreen.ui.AuthScreen
import com.tride.admin.mainui.driverdetailscreen.ui.DriverDetailScreen
import com.tride.admin.mainui.earningsscreen.ui.EarningsScreen
import com.tride.admin.mainui.editprofilescreen.ui.EditProfileScreen // Import Added
import com.tride.admin.mainui.managedriversscreen.ui.ManageDriversScreen
import com.tride.admin.mainui.manageridersscreen.ui.ManageRidersScreen
import com.tride.admin.mainui.notificationscreen.ui.NotificationScreen
import com.tride.admin.mainui.otpscreen.ui.OtpScreen
import com.tride.admin.mainui.ridedetailscreen.ui.RideDetailScreen
import com.tride.admin.mainui.riderdetailscreen.ui.RiderDetailScreen
import com.tride.admin.mainui.ridehistoryscreen.ui.RideHistoryScreen
import com.tride.admin.mainui.settingsscreen.ui.SettingScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) {
        // ... (Previous routes 1-12 remain unchanged) ...
        composable(route = Screen.AuthScreen.route) { AuthScreen(navController = navController) }
        composable(
            route = Screen.OtpScreen.route,
            arguments = listOf(navArgument("mobileNumber") { type = NavType.StringType })
        ) { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            OtpScreen(navController = navController, mobileNumber = mobileNumber)
        }
        composable(route = Screen.AdminScreen.route) { AdminScreen(navController = navController) }
        composable(route = Screen.ManageDriversScreen.route) { ManageDriversScreen(navController = navController) }
        composable(route = Screen.ManageRidersScreen.route) { ManageRidersScreen(navController = navController) }
        composable(route = Screen.RideHistoryScreen.route) { RideHistoryScreen(navController = navController) }
        composable(route = Screen.SettingScreen.route) { SettingScreen(navController = navController) }

        composable(
            route = Screen.RideDetailScreen.route,
            arguments = listOf(navArgument("rideId") { type = NavType.StringType })
        ) { backStackEntry ->
            val rideId = backStackEntry.arguments?.getString("rideId") ?: ""
            RideDetailScreen(navController = navController, rideId = rideId)
        }
        composable(
            route = Screen.DriverDetailScreen.route,
            arguments = listOf(navArgument("driverId") { type = NavType.StringType })
        ) { backStackEntry ->
            val driverId = backStackEntry.arguments?.getString("driverId") ?: ""
            DriverDetailScreen(navController = navController, driverId = driverId)
        }
        composable(
            route = Screen.RiderDetailScreen.route,
            arguments = listOf(navArgument("riderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val riderId = backStackEntry.arguments?.getString("riderId") ?: ""
            RiderDetailScreen(navController = navController, riderId = riderId)
        }
        composable(route = Screen.NotificationScreen.route) { NotificationScreen(navController = navController) }
        composable(route = Screen.EarningsScreen.route) { EarningsScreen(navController = navController) }

        // 13. Edit Profile (ADDED)
        composable(route = Screen.EditProfileScreen.route) {
            EditProfileScreen(navController = navController)
        }
    }
}