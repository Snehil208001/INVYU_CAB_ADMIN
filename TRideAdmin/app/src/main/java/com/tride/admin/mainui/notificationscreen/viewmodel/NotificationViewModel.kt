package com.tride.admin.mainui.notificationscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class NotificationUiModel(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: String // "Alert", "Success", "Info"
)

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {

    private val _notifications = mutableStateOf<List<NotificationUiModel>>(emptyList())
    val notifications: State<List<NotificationUiModel>> = _notifications

    init {
        loadMockNotifications()
    }

    private fun loadMockNotifications() {
        _notifications.value = listOf(
            NotificationUiModel(
                "1", "SOS Alert!", "Driver Ramesh Kumar pressed SOS button on Ride #TR1024.", "Just Now", "Alert"
            ),
            NotificationUiModel(
                "2", "New Driver Registered", "Suresh Singh has submitted documents for verification.", "10 mins ago", "Info"
            ),
            NotificationUiModel(
                "3", "Payment Received", "Weekly settlement of ₹45,000 processed successfully.", "2 hrs ago", "Success"
            ),
            NotificationUiModel(
                "4", "High Demand Area", "Surge pricing activated in Boring Road area due to high traffic.", "5 hrs ago", "Info"
            ),
            NotificationUiModel(
                "5", "Document Expiring", "Vehicle insurance for Driver ID #405 is expiring tomorrow.", "Yesterday", "Alert"
            )
        )
    }
}