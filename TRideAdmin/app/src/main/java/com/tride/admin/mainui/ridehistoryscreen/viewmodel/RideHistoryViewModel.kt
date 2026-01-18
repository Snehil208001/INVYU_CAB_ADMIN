package com.tride.admin.mainui.ridehistoryscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class RideUiModel(
    val id: String,
    val pickup: String,
    val drop: String,
    val driverName: String,
    val userName: String,
    val price: String,
    val date: String,
    val status: String // "Completed", "Cancelled"
)

@HiltViewModel
class RideHistoryViewModel @Inject constructor() : ViewModel() {

    private val _rides = mutableStateOf<List<RideUiModel>>(emptyList())
    val rides: State<List<RideUiModel>> = _rides

    init {
        loadMockHistory()
    }

    private fun loadMockHistory() {
        _rides.value = listOf(
            RideUiModel("TR1024", "Gandhi Maidan, Patna", "Patna Junction", "Raju Singh", "Rahul Kumar", "₹145", "Today, 10:30 AM", "Completed"),
            RideUiModel("TR1023", "Boring Road", "Airport", "Vijay Yadav", "Priya Singh", "₹320", "Yesterday, 06:15 PM", "Completed"),
            RideUiModel("TR1022", "Kankarbagh", "Danapur", "Amit Kumar", "Sneha Gupta", "₹210", "Yesterday, 04:00 PM", "Cancelled"),
            RideUiModel("TR1021", "Zoo Gate 1", "Bailey Road", "Raju Singh", "Ankit Raj", "₹95", "12 Jan, 02:20 PM", "Completed"),
            RideUiModel("TR1020", "Saguna More", "Hitech City", "Sunil Verma", "Kavita Das", "₹180", "12 Jan, 09:00 AM", "Completed")
        )
    }
}