package com.tripmate.ai.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Timeline : Screen("timeline/{tripId}") {
        fun createRoute(tripId: Long) = "timeline/$tripId"
    }
    object MapNavigation : Screen("map/{dayId}") {
        fun createRoute(dayId: Long) = "map/$dayId"
    }
}
