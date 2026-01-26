package com.tride.admin.mainui.manageridersscreen.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.repository.ManageRidersRepository
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
class ManageRidersViewModel @Inject constructor(
    private val repository: ManageRidersRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _riders = mutableStateOf<List<RiderUiModel>>(emptyList())
    val riders: State<List<RiderUiModel>> = _riders

    private val _eventFlow = MutableSharedFlow<BaseViewModel.UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadRiders()
    }

    private fun loadRiders() {
        val currentUserEmail = auth.currentUser?.email
        Log.d("ManageRidersVM", "Loading riders. Current Admin Email: $currentUserEmail")

        if (currentUserEmail != null) {
            viewModelScope.launch {
                val result = repository.getRiders(currentUserEmail)

                when (result) {
                    is Resource.Success -> {
                        val apiList = result.data?.userStatistics ?: emptyList()
                        Log.d("ManageRidersVM", "Received ${apiList.size} riders from API")

                        val mappedList = apiList.map { dto ->
                            val ratingVal = try {
                                dto.rating?.toDouble() ?: 0.0
                            } catch (e: Exception) {
                                0.0
                            }

                            // --- FIXED LOGIC HERE ---
                            // 1. Check if status field explicitly says block
                            val apiStatus = dto.status?.lowercase() ?: ""
                            val isExplicitlyBlocked = apiStatus == "block" || apiStatus == "blocked"

                            val status = if (isExplicitlyBlocked) {
                                "Blocked"
                            } else if (dto.isVerified == 1) {
                                "Active"
                            } else {
                                "Blocked" // Default to Blocked if not verified and not explicitly active
                            }

                            RiderUiModel(
                                id = dto.userId.toString(),
                                name = dto.fullName ?: "Unknown",
                                phone = dto.phoneNumber ?: "N/A",
                                ridesTaken = dto.totalCompletedRides ?: 0,
                                rating = ratingVal,
                                status = status
                            )
                        }
                        _riders.value = mappedList
                    }
                    is Resource.Error -> {
                        Log.e("ManageRidersVM", "Error loading riders: ${result.message}")
                        _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar(result.message ?: "Failed to load riders"))
                    }
                    is Resource.Loading -> {
                        Log.d("ManageRidersVM", "Riders loading...")
                    }
                }
            }
        } else {
            Log.e("ManageRidersVM", "No Admin Email found in FirebaseAuth")
            viewModelScope.launch {
                _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar("Admin email not found"))
            }
        }
    }

    fun onBlockUnblockClicked(rider: RiderUiModel) {
        val currentUserEmail = auth.currentUser?.email
        if (currentUserEmail == null) {
            Log.e("ManageRidersVM", "Action failed: Admin email is null")
            return
        }

        val isCurrentlyActive = rider.status == "Active"
        val apiStatus = if (isCurrentlyActive) "block" else "active"

        Log.d("ManageRidersVM", "Action Clicked for User: ${rider.name} (ID: ${rider.id}). Current Status: ${rider.status}. Requesting API Status: $apiStatus")

        // Optimistic UI Update
        val newUiStatus = if (isCurrentlyActive) "Blocked" else "Active"
        val updatedList = _riders.value.map {
            if (it.id == rider.id) {
                it.copy(status = newUiStatus)
            } else it
        }
        _riders.value = updatedList

        viewModelScope.launch {
            try {
                val userId = rider.id.toInt()
                val result = repository.updateUserStatus(currentUserEmail, userId, apiStatus)

                when (result) {
                    is Resource.Success -> {
                        Log.d("ManageRidersVM", "API Success: User ${rider.id} updated to $newUiStatus")
                        _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar("User status updated to $newUiStatus"))
                    }
                    is Resource.Error -> {
                        Log.e("ManageRidersVM", "API Error for User ${rider.id}: ${result.message}")

                        // Revert UI on failure
                        val revertedList = _riders.value.map {
                            if (it.id == rider.id) {
                                it.copy(status = rider.status) // Revert to old status
                            } else it
                        }
                        _riders.value = revertedList
                        _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar(result.message ?: "Failed to update status"))
                    }
                    is Resource.Loading -> {}
                }
            } catch (e: NumberFormatException) {
                Log.e("ManageRidersVM", "Invalid ID format for user: ${rider.id}")
                _eventFlow.emit(BaseViewModel.UiEvent.ShowSnackbar("Invalid User ID"))
            }
        }
    }
}