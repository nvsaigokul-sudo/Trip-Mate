package com.tripmate.ai.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripmate.ai.data.local.entity.TripEntity
import com.tripmate.ai.ui.theme.NeonCyan
import com.tripmate.ai.ui.theme.NeonPurple
import com.tripmate.ai.ui.theme.SurfaceDarkGlass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onTripSelected: (Long) -> Unit
) {
    val recentTrips by viewModel.recentTrips.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val generatedTripId by viewModel.generatedTripId.collectAsState()

    var prompt by remember { mutableStateOf("") }

    LaunchedEffect(generatedTripId) {
        generatedTripId?.let {
            onTripSelected(it)
            viewModel.clearGeneratedTripId()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Trip Mate AI",
                style = MaterialTheme.typography.displayLarge,
                color = NeonCyan,
                modifier = Modifier.padding(top = 32.dp, bottom = 8.dp)
            )
            Text(
                text = "Where do you want to go next?",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            // AI Prompt Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkGlass),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                        placeholder = { Text("Plan a 3-day Goa trip under ₹10,000", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = NeonCyan,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (prompt.isNotBlank()) viewModel.generateTrip(prompt)
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(NeonCyan, NeonPurple)
                                )
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        enabled = !isLoading && prompt.isNotBlank()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        } else {
                            Icon(Icons.Default.Send, contentDescription = "Generate", tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Recent Trips",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(recentTrips) { trip ->
                    TripCard(trip = trip) {
                        onTripSelected(trip.id)
                    }
                }
            }
        }
    }
}

@Composable
fun TripCard(trip: TripEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = SurfaceDarkGlass)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = trip.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Duration: ${trip.durationDays} Days • Budget: ${trip.budget}",
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
    }
}
