package com.tripmate.ai.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tripmate.ai.ui.theme.NeonCyan
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val alphaAnim = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        // Fade in animation
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
        // Keep splash screen visible for 1 second after fade in
        delay(1000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // A simple circular glowing placeholder for the Lottie animation
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alphaAnim.value)
                    .background(NeonCyan, shape = androidx.compose.foundation.shape.CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "TRIP MATE AI",
                color = NeonCyan,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(alphaAnim.value),
                style = MaterialTheme.typography.displayLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Your Intelligent Travel Companion",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.alpha(alphaAnim.value)
            )
        }
    }
}
