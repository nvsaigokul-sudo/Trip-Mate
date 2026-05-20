package com.tripmate.ai.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.*
import android.Manifest
import android.annotation.SuppressLint
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.tripmate.ai.ui.theme.NeonCyan
import com.tripmate.ai.ui.theme.SurfaceDarkGlass

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NavigationScreen(
    viewModel: NavigationViewModel,
    dayId: Long,
    navController: NavController
) {
    val currentLocation by viewModel.currentLocation.collectAsState()
    val waypoints by viewModel.routeWaypoints.collectAsState()
    val places by viewModel.places.collectAsState()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    
    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        } else {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.updateCurrentLocation(location.latitude, location.longitude)
                    }
                }
        }
    }

    LaunchedEffect(dayId) {
        viewModel.loadDayRoute(dayId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Map Layer
        OsmMapView(
            modifier = Modifier.fillMaxSize(),
            currentLocation = currentLocation,
            waypoints = waypoints,
            onMapReady = { map ->
                // Additional map configuration if needed
            }
        )

        // Top HUD
        TopAppBar(
            title = { Text("Live Navigation", color = Color.White) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = SurfaceDarkGlass
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Bottom HUD
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = SurfaceDarkGlass),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Next Stop",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = places.firstOrNull()?.name ?: "Calculating...",
                        color = NeonCyan,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                FloatingActionButton(
                    onClick = { /* Recenter Map */ },
                    containerColor = NeonCyan
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Recenter", tint = Color.Black)
                }
            }
        }
    }
}
