package com.tride.admin.mainui.driverdetailscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class DriverDetailUiModel(
    val id: String,
    val name: String,
    val phone: String,
    // Expanded Vehicle Details
    val vehicleNumber: String,
    val vehicleModel: String,
    val vehicleType: String,      // New (e.g., Sedan)
    val vehicleColor: String,     // New (e.g., White)
    val vehicleCapacity: String,  // New (e.g., 4)

    val status: String,
    val rating: Double,
    val totalRides: String,
    val totalEarnings: String
)

@HiltViewModel
class DriverDetailViewModel @Inject constructor() : ViewModel() {

    // In a real app, use SavedStateHandle to get 'driverId' and fetch from Repo
    private val _driver = mutableStateOf(
        DriverDetailUiModel(
            id = "1",
            name = "Ramesh Kumar",
            phone = "+91 9876543210",

            // Updated Vehicle Data matching your preferences screen
            vehicleNumber = "BR-01-AB-1234",
            vehicleModel = "Swift Dzire",
            vehicleType = "Sedan",
            vehicleColor = "White",
            vehicleCapacity = "4 Seats",

            status = "Active",
            rating = 4.8,
            totalRides = "1,240",
            totalEarnings = "₹45,200"
        )
    )
    val driver: State<DriverDetailUiModel> = _driver
}