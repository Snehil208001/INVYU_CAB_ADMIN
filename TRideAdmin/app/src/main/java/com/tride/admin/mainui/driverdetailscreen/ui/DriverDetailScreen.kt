package com.tride.admin.mainui.driverdetailscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tride.admin.mainui.driverdetailscreen.viewmodel.DriverDetailViewModel
import com.tride.admin.ui.theme.CabMintGreen
import com.tride.admin.ui.theme.CabVeryLightMint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverDetailScreen(
    navController: NavController,
    driverId: String,
    viewModel: DriverDetailViewModel = hiltViewModel()
) {
    val driver = viewModel.driver.value

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = { Text("Driver Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Header Profile Card
            ProfileCard(driver.name, driver.phone, driver.rating, driver.status)

            // 2. Stats Row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatBox(title = "Total Rides", value = driver.totalRides, icon = Icons.Default.DirectionsCar, modifier = Modifier.weight(1f))
                StatBox(title = "Earnings", value = driver.totalEarnings, icon = Icons.Default.AccountBalanceWallet, modifier = Modifier.weight(1f))
                StatBox(title = "Joined", value = "Jan 2025", icon = Icons.Default.CalendarToday, modifier = Modifier.weight(1f))
            }

            // 3. Vehicle Details (UPDATED)
            SectionTitle("Vehicle Information")
            VehicleInfoCard(
                model = driver.vehicleModel,
                number = driver.vehicleNumber,
                type = driver.vehicleType,
                color = driver.vehicleColor,
                capacity = driver.vehicleCapacity
            )

            // 4. Documents (Mock)
            SectionTitle("Documents")
            DocumentCard("Driving License", "Verified", Icons.Default.Badge)
            DocumentCard("Vehicle Insurance", "Pending", Icons.Default.Description)

            // 5. Actions
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { /* Call Driver */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = CabMintGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call")
                }

                OutlinedButton(
                    onClick = { /* Block Logic */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Block, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Block")
                }
            }
        }
    }
}

@Composable
fun ProfileCard(name: String, phone: String, rating: Double, status: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(CabVeryLightMint),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, null, tint = CabMintGreen, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(phone, color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                    Text(" $rating Rating", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            StatusChip(status)
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (color, bg) = when(status) {
        "Active" -> Color(0xFF2E7D32) to Color(0xFFE8F5E9)
        "Blocked" -> Color(0xFFC62828) to Color(0xFFFFEBEE)
        else -> Color(0xFFEF6C00) to Color(0xFFFFF3E0)
    }
    Surface(color = bg, shape = RoundedCornerShape(4.dp)) {
        Text(
            text = status,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun StatBox(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(title, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

// --- UPDATED VEHICLE CARD ---
@Composable
fun VehicleInfoCard(
    model: String,
    number: String,
    type: String,
    color: String,
    capacity: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            // Main Row: Icon + Model + Number
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DirectionsCar, null, tint = CabMintGreen, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(model, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(number, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(16.dp))

            // Details Grid
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                VehicleDetailItem("Type", type)
                VehicleDetailItem("Color", color)
                VehicleDetailItem("Capacity", capacity)
            }
        }
    }
}

@Composable
fun VehicleDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
fun DocumentCard(title: String, status: String, icon: ImageVector) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Color.Gray)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium)
            if (status == "Verified") {
                Icon(Icons.Default.CheckCircle, null, tint = CabMintGreen, modifier = Modifier.size(20.dp))
            } else {
                Icon(Icons.Default.Pending, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp, start = 4.dp)
    )
}