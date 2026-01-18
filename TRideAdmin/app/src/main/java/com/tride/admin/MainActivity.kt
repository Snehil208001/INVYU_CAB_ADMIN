package com.tride.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.tride.admin.core.navigations.NavGraph
import com.tride.admin.ui.theme.TRideAdminTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TRideAdminTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}