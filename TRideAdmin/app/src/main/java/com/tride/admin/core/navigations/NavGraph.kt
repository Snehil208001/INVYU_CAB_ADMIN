package com.tride.admin.core.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tride.admin.mainui.adminscreen.ui.AdminScreen
import com.tride.admin.mainui.authscreen.ui.AuthScreen
import com.tride.admin.mainui.managedriversscreen.ui.ManageDriversScreen
import com.tride.admin.mainui.otpscreen.ui.OtpScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) {
        // 1. Auth Screen
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(navController = navController)
        }

        // 2. OTP Screen (Accepts mobileNumber)
        composable(
            route = Screen.OtpScreen.route,
            arguments = listOf(navArgument("mobileNumber") { type = NavType.StringType })
        ) { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            OtpScreen(navController = navController, mobileNumber = mobileNumber)
        }

        // 3. Admin Dashboard
        composable(route = Screen.AdminScreen.route) {
            AdminScreen(navController = navController)
        }

        // 4. Manage Drivers
        composable(route = Screen.ManageDriversScreen.route) {
            ManageDriversScreen(navController = navController)
        }
    }
}