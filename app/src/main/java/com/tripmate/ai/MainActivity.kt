package com.tripmate.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tripmate.ai.navigation.Screen
import com.tripmate.ai.ui.ViewModelFactory
import com.tripmate.ai.ui.home.HomeScreen
import com.tripmate.ai.ui.home.HomeViewModel
import com.tripmate.ai.ui.map.NavigationScreen
import com.tripmate.ai.ui.map.NavigationViewModel
import com.tripmate.ai.ui.theme.TripMateAITheme
import com.tripmate.ai.ui.timeline.TimelineScreen
import com.tripmate.ai.ui.timeline.TimelineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val app = application as TripMateApplication
        val factory = ViewModelFactory(app.tripRepository)

        setContent {
            TripMateAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.Splash.route) {
                        composable(Screen.Splash.route) {
                            com.tripmate.ai.ui.splash.SplashScreen(
                                onSplashFinished = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.Splash.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Home.route) {
                            val viewModel: HomeViewModel = viewModel(factory = factory)
                            HomeScreen(
                                viewModel = viewModel,
                                onTripSelected = { tripId ->
                                    navController.navigate(Screen.Timeline.createRoute(tripId))
                                }
                            )
                        }
                        composable(
                            route = Screen.Timeline.route,
                            arguments = listOf(navArgument("tripId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val tripId = backStackEntry.arguments?.getLong("tripId") ?: return@composable
                            val viewModel: TimelineViewModel = viewModel(factory = factory)
                            TimelineScreen(
                                viewModel = viewModel,
                                tripId = tripId,
                                onNavigateToMap = { dayId ->
                                    navController.navigate(Screen.MapNavigation.createRoute(dayId))
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            route = Screen.MapNavigation.route,
                            arguments = listOf(navArgument("dayId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val dayId = backStackEntry.arguments?.getLong("dayId") ?: return@composable
                            val viewModel: NavigationViewModel = viewModel(factory = factory)
                            NavigationScreen(
                                viewModel = viewModel,
                                dayId = dayId,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
