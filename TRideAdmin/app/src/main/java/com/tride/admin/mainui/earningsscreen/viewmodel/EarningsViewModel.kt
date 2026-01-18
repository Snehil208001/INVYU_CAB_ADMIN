package com.tride.admin.mainui.earningsscreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class EarningsUiState(
    val totalRevenue: String = "₹12,450.00",
    val todaysEarnings: String = "₹1,240"
)

data class TransactionUiModel(
    val id: String,
    val title: String, // e.g., "Ride Commission #TR1024"
    val time: String,
    val amount: String
)

@HiltViewModel
class EarningsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = mutableStateOf(EarningsUiState())
    val uiState: State<EarningsUiState> = _uiState

    private val _transactions = mutableStateOf<List<TransactionUiModel>>(emptyList())
    val transactions: State<List<TransactionUiModel>> = _transactions

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _transactions.value = listOf(
            TransactionUiModel("1", "Commission - Ride #TR1024", "Today, 10:45 AM", "+ ₹25.00"),
            TransactionUiModel("2", "Commission - Ride #TR1023", "Today, 09:30 AM", "+ ₹42.50"),
            TransactionUiModel("3", "Driver Payout - Ramesh", "Yesterday, 06:00 PM", "- ₹4,200.00"),
            TransactionUiModel("4", "Commission - Ride #TR1020", "Yesterday, 04:15 PM", "+ ₹18.00"),
            TransactionUiModel("5", "Commission - Ride #TR1019", "12 Jan, 08:00 PM", "+ ₹35.00")
        )
    }
}