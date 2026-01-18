package com.tride.admin.mainui.ridehistoryscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tride.admin.mainui.ridehistoryscreen.viewmodel.RideHistoryViewModel
import com.tride.admin.mainui.ridehistoryscreen.viewmodel.RideUiModel
import com.tride.admin.ui.theme.CabMintGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideHistoryScreen(
    navController: NavController,
    viewModel: RideHistoryViewModel = hiltViewModel()
) {
    val rides = viewModel.rides.value

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = { Text("Ride History", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rides) { ride ->
                RideHistoryItem(ride)
            }
        }
    }
}

@Composable
fun RideHistoryItem(ride: RideUiModel) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: ID and Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${ride.id}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = ride.price,
                    fontWeight = FontWeight.ExtraBold,
                    color = CabMintGreen,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(8.dp))

            // Route
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Circle, null, modifier = Modifier.size(8.dp), tint = CabMintGreen)
                    Box(modifier = Modifier.width(2.dp).height(20.dp).background(Color.LightGray))
                    Icon(Icons.Default.Navigation, null, modifier = Modifier.size(10.dp), tint = Color.Red)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = ride.pickup, fontSize = 14.sp, maxLines = 1, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = ride.drop, fontSize = 14.sp, maxLines = 1, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Status and Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "${ride.driverName} • ${ride.userName}", fontSize = 12.sp, color = Color.Gray)
                    Text(text = ride.date, fontSize = 11.sp, color = Color.Gray)
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = if (ride.status == "Completed") Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ) {
                    Text(
                        text = ride.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (ride.status == "Completed") Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }
        }
    }
}