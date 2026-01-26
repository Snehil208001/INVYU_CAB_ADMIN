package com.tride.admin.mainui.managedriversscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.tride.admin.core.base.BaseViewModel
import com.tride.admin.core.utils.Resource
import com.tride.admin.data.repository.ManageDriversRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Keep your UiModel as is
data class DriverUiModel(
    val id: Int,
    val name: String,
    val phone: String,
    val vehicleModel: String,
    val vehicleNumber: String,
    val status: String, // "Active", "Pending", "Blocked"
    val rating: Double
)

@HiltViewModel
class ManageDriversViewModel @Inject constructor(
    private val repository: ManageDriversRepository
) : BaseViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _drivers = mutableStateOf<List<DriverUiModel>>(emptyList())
    val drivers: State<List<DriverUiModel>> = _drivers

    private val _filteredDrivers = mutableStateOf<List<DriverUiModel>>(emptyList())
    val filteredDrivers: State<List<DriverUiModel>> = _filteredDrivers

    private val _selectedFilter = mutableStateOf("All")
    val selectedFilter: State<String> = _selectedFilter

    // Search State
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadDrivers()
    }

    private fun loadDrivers() {
        val currentUserEmail = auth.currentUser?.email

        if (currentUserEmail != null) {
            _isLoading.value = true
            viewModelScope.launch {
                val result = repository.getDrivers(currentUserEmail)

                when (result) {
                    is Resource.Success -> {
                        val apiList = result.data?.userStatistics ?: emptyList()

                        // Map API DTO to UI Model
                        val mappedList = apiList.map { dto ->
                            // Determine status based on API fields
                            // If isVerified == 1 -> Active, else Pending.
                            val status = if (dto.isVerified == 1) "Active" else "Pending"

                            DriverUiModel(
                                id = dto.userId,
                                name = dto.fullName ?: "Unknown",
                                phone = dto.phoneNumber ?: "N/A",
                                vehicleModel = dto.model ?: "N/A",
                                vehicleNumber = dto.vehicleNumber ?: "N/A",
                                status = status,
                                rating = dto.rating ?: 0.0
                            )
                        }

                        _drivers.value = mappedList
                        applyFilter()
                        _isLoading.value = false
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        sendEvent(UiEvent.ShowSnackbar(result.message ?: "Failed to fetch drivers"))
                    }
                    is Resource.Loading -> {
                        // Handled by _isLoading.value
                    }
                }
            }
        } else {
            sendEvent(UiEvent.ShowSnackbar("Admin email not found. Please login again."))
        }
    }

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
        applyFilter()
    }

    // Search Logic
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    // Filter Logic
    private fun applyFilter() {
        val query = _searchQuery.value.trim().lowercase()
        val filter = _selectedFilter.value

        _filteredDrivers.value = _drivers.value.filter { driver ->
            // 1. Check Status
            val matchesStatus = if (filter == "All") true else driver.status == filter

            // 2. Check Search (Name or Phone)
            val matchesSearch = if (query.isEmpty()) true else {
                driver.name.lowercase().contains(query) || driver.phone.contains(query)
            }

            matchesStatus && matchesSearch
        }
    }

    fun onApproveClicked(driver: DriverUiModel) {
        // Logic to approve driver via API would go here
        sendEvent(UiEvent.ShowSnackbar("${driver.name} Approved successfully."))
        updateDriverStatus(driver.id, "Active")
    }

    fun onBlockClicked(driver: DriverUiModel) {
        // Logic to block driver via API would go here
        val newStatus = if (driver.status == "Blocked") "Active" else "Blocked"
        sendEvent(UiEvent.ShowSnackbar("${driver.name} is now $newStatus."))
        updateDriverStatus(driver.id, newStatus)
    }

    private fun updateDriverStatus(id: Int, newStatus: String) {
        val updatedList = _drivers.value.map {
            if (it.id == id) it.copy(status = newStatus) else it
        }
        _drivers.value = updatedList
        applyFilter() // Re-apply filters to refresh list
    }
}