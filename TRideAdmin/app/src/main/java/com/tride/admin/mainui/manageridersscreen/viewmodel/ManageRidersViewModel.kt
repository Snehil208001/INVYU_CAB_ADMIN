package com.tride.admin.mainui.manageridersscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tride.admin.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RiderUiModel(
    val id: String,
    val name: String,
    val phone: String,
    val ridesTaken: Int,
    val rating: Double,
    val status: String // "Active", "Blocked"
)

@HiltViewModel
class ManageRidersViewModel @Inject constructor() : ViewModel() {

    private val _riders = mutableStateOf<List<RiderUiModel>>(emptyList())
    val riders: State<List<RiderUiModel>> = _riders

    private val _eventFlow = MutableSharedFlow<BaseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadMockRiders()
    }

    private fun loadMockRiders() {
        _riders.value = listOf(
            RiderUiModel("1", "Rahul Kumar", "+91 9876543210", 12, 4.8, "Active"),
            RiderUiModel("2", "Priya Singh", "+91 9123456789", 5, 4.5, "Active"),
            RiderUiModel("3", "Amit Sharma", "+91 8765432109", 0, 0.0, "Blocked"),
            RiderUiModel("4", "Sneha Gupta", "+91 7654321098", 22, 4.9, "Active")
        )
    }

    fun onBlockUnblockClicked(rider: RiderUiModel) {
        // Toggle logic for UI demo
        val updatedList = _riders.value.map {
            if (it.id == rider.id) {
                it.copy(status = if (it.status == "Active") "Blocked" else "Active")
            } else it
        }
        _riders.value = updatedList

        viewModelScope.launch {
            _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar("${rider.name} status updated"))
        }
    }
}