package com.tripmate.ai.ui.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.tripmate.ai.data.local.entity.PlaceEntity
import com.tripmate.ai.ui.theme.NeonCyan
import com.tripmate.ai.ui.theme.SurfaceDarkGlass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    tripId: Long,
    onNavigateToMap: (Long) -> Unit,
    onBackClick: () -> Unit
) {
    val trip by viewModel.trip.collectAsState()
    val days by viewModel.days.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(tripId) {
        viewModel.loadTrip(tripId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(trip?.title ?: "Trip Details", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = NeonCyan)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(days) { dayWithPlaces ->
                    DayCard(
                        dayWithPlaces = dayWithPlaces,
                        onStartNavigation = { onNavigateToMap(dayWithPlaces.day.dayId) }
                    )
                }
            }
        }
    }
}

@Composable
fun DayCard(dayWithPlaces: DayWithPlaces, onStartNavigation: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDarkGlass)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Day ${dayWithPlaces.day.dayNumber}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan
                )
                Button(
                    onClick = onStartNavigation,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan)
                ) {
                    Text("Start Day", color = Color.Black)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = dayWithPlaces.day.description,
                fontSize = 14.sp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            dayWithPlaces.places.forEach { place ->
                PlaceItem(place = place)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PlaceItem(place: PlaceEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = NeonCyan,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${place.timeHint} - ${place.name}",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = place.description,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
