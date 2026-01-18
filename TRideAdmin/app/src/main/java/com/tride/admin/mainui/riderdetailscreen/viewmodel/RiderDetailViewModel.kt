package com.tride.admin.mainui.riderdetailscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class RiderDetailUiModel(
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val status: String,
    val totalRides: String,
    val rating: Double,
    val cancelledRides: String
)

@HiltViewModel
class RiderDetailViewModel @Inject constructor() : ViewModel() {

    // Mock data - In real app, fetch using 'riderId'
    private val _rider = mutableStateOf(
        RiderDetailUiModel(
            id = "1",
            name = "Rahul Kumar",
            phone = "+91 9876543210",
            email = "rahul.kumar@example.com",
            status = "Active",
            totalRides = "42",
            rating = 4.8,
            cancelledRides = "2"
        )
    )
    val rider: State<RiderDetailUiModel> = _rider
}