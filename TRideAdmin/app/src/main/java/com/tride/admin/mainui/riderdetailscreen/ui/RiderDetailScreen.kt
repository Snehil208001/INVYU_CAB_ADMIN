package com.tride.admin.mainui.riderdetailscreen.ui

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
import com.tride.admin.mainui.riderdetailscreen.viewmodel.RiderDetailViewModel
import com.tride.admin.ui.theme.CabMintGreen
import com.tride.admin.ui.theme.CabVeryLightMint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiderDetailScreen(
    navController: NavController,
    riderId: String,
    viewModel: RiderDetailViewModel = hiltViewModel()
) {
    val rider = viewModel.rider.value

    Scaffold(
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = { Text("Rider Profile", fontWeight = FontWeight.Bold) },
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
            // 1. Profile Header
            RiderProfileCard(rider.name, rider.phone, rider.email, rider.status)

            // 2. Statistics
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                RiderStatBox("Total Rides", rider.totalRides, Icons.Default.DirectionsCar, Modifier.weight(1f))
                RiderStatBox("Avg Rating", rider.rating.toString(), Icons.Default.Star, Modifier.weight(1f))
                RiderStatBox("Cancelled", rider.cancelledRides, Icons.Default.Cancel, Modifier.weight(1f))
            }

            // 3. Contact Actions
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { /* Call Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = CabMintGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Call, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call")
                }
                OutlinedButton(
                    onClick = { /* Email Action */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Email, null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Email")
                }
            }

            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))

            // 4. Saved Places (Mock)
            Text("Saved Locations", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            SavedLocationItem("Home", "Kankarbagh, Patna", Icons.Default.Home)
            SavedLocationItem("Work", "Boring Road, Patna", Icons.Default.Work)

            Spacer(modifier = Modifier.height(8.dp))

            // 5. Block Action
            OutlinedButton(
                onClick = { /* Block Logic */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
            ) {
                Icon(Icons.Default.Block, null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (rider.status == "Blocked") "Unblock User" else "Block User")
            }
        }
    }
}

@Composable
fun RiderProfileCard(name: String, phone: String, email: String, status: String) {
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
                Text(email, color = Color.Gray, fontSize = 12.sp)
            }
            Surface(
                color = if (status == "Active") Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = status,
                    color = if (status == "Active") Color(0xFF2E7D32) else Color(0xFFC62828),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RiderStatBox(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
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

@Composable
fun SavedLocationItem(label: String, address: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = CabMintGreen, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(address, color = Color.Gray, fontSize = 12.sp)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}