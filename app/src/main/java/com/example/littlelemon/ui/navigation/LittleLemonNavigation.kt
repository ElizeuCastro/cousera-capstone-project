package com.example.littlelemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.data.reporitory.SectionRepository
import com.example.littlelemon.ui.screen.HomeScreen
import com.example.littlelemon.ui.screen.OnboardingScreen
import com.example.littlelemon.ui.screen.ProfileScreen

@Composable
fun LittleLemonNavigation(
    navHostController: NavHostController,
    sectionRepository: SectionRepository
) {

    val startDestination =
        if (sectionRepository.isUserLoggedIn()) Destinations.Home.route else Destinations.Onboarding.route

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(route = Destinations.Onboarding.route) {
            OnboardingScreen(navHostController)
        }
        composable(route = Destinations.Profile.route) {
            ProfileScreen(navHostController)
        }
        composable(route = Destinations.Home.route) {
            HomeScreen(navHostController)
        }
    }
}