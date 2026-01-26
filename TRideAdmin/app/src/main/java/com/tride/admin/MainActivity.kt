package com.tride.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.tride.admin.core.navigations.NavGraph
import com.tride.admin.core.navigations.Screen
import com.tride.admin.ui.theme.TRideAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check for existing session
        val auth = FirebaseAuth.getInstance()
        val startDestination = if (auth.currentUser != null) {
            Screen.AdminScreen.route
        } else {
            Screen.AuthScreen.route
        }

        setContent {
            TRideAdminTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Pass the dynamic startDestination to NavGraph
                    NavGraph(navController = navController, startDestination = startDestination)
                }
            }
        }
    }
}