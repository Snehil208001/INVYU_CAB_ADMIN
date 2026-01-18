package com.tride.admin.mainui.managedriversscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.tride.admin.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

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
class ManageDriversViewModel @Inject constructor() : BaseViewModel() {

    private val _drivers = mutableStateOf<List<DriverUiModel>>(emptyList())
    val drivers: State<List<DriverUiModel>> = _drivers

    private val _filteredDrivers = mutableStateOf<List<DriverUiModel>>(emptyList())
    val filteredDrivers: State<List<DriverUiModel>> = _filteredDrivers

    private val _selectedFilter = mutableStateOf("All")
    val selectedFilter: State<String> = _selectedFilter

    // ✅ NEW: Search State
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        loadDrivers()
    }

    private fun loadDrivers() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(1000) // Simulate Network

            val mockList = listOf(
                DriverUiModel(1, "Ramesh Kumar", "+91 9876543210", "Swift Dzire", "BR-01-AB-1234", "Active", 4.8),
                DriverUiModel(2, "Suresh Singh", "+91 9876500000", "Honda City", "BR-01-XY-9876", "Pending", 0.0),
                DriverUiModel(3, "Mahesh Yadav", "+91 9998887776", "Tata Nexon", "BR-01-ZZ-1122", "Active", 4.5),
                DriverUiModel(4, "Dinesh Verma", "+91 8887776665", "Mahindra XUV", "BR-01-MN-4567", "Blocked", 3.2),
                DriverUiModel(5, "Rajesh Gupta", "+91 7776665554", "Maruti Alto", "BR-01-PQ-3344", "Pending", 0.0),
                DriverUiModel(6, "Vikram Malhotra", "+91 9122334455", "Toyota Innova", "BR-01-CC-8899", "Active", 4.9)
            )

            _drivers.value = mockList
            applyFilter() // Apply initial filter
            _isLoading.value = false
        }
    }

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
        applyFilter()
    }

    // ✅ NEW: Search Logic
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        applyFilter()
    }

    // ✅ UPDATED: Filter Logic to check both Status AND Search Query
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
        sendEvent(UiEvent.ShowSnackbar("${driver.name} Approved successfully."))
        updateDriverStatus(driver.id, "Active")
    }

    fun onBlockClicked(driver: DriverUiModel) {
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