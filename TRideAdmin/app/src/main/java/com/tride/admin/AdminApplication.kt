package com.tride.admin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdminApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize other global libraries here if needed (e.g., Timber, FirebaseApp)
    }
}