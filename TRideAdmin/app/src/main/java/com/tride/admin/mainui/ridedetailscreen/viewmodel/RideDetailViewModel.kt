package com.tride.admin.mainui.ridedetailscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tride.admin.mainui.ridehistoryscreen.viewmodel.RideUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RideDetailViewModel @Inject constructor() : ViewModel() {

    // In a real app, you would fetch this by ID
    private val _rideDetails = mutableStateOf(
        RideUiModel(
            id = "TR1024",
            pickup = "Gandhi Maidan, Patna, Bihar",
            drop = "Patna Junction, Bihar",
            driverName = "Raju Singh",
            userName = "Rahul Kumar",
            price = "₹145",
            date = "Today, 10:30 AM",
            status = "Completed"
        )
    )
    val rideDetails: State<RideUiModel> = _rideDetails
}