package com.pose_estimation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pose_estimation.ui.screens.ImageSelectorScreen
import com.pose_estimation.ui.screens.home.HomeScreen
import com.pose_estimation.ui.screens.results.ResultScreen
import com.pose_estimation.ui.screens.SettingsScreen
import com.pose_estimation.ui.screens.UserProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen
    ){
        home(navController = navController)
        result(navController = navController)
        userProfile(navController = navController)
        settings(navController = navController)
        imageSelector(navController = navController)
    }
}

private fun NavGraphBuilder.home(navController: NavHostController) {
    composable<AppScreens.HomeScreen> { HomeScreen(navController) }
}

private fun NavGraphBuilder.userProfile(navController: NavHostController) {
    composable<AppScreens.UserProfileScreen> { UserProfileScreen() }
}

private fun NavGraphBuilder.settings(navController: NavHostController) {
    composable<AppScreens.SettingsScreen> { SettingsScreen(navController) }
}

private fun NavGraphBuilder.result(navController: NavHostController) {
    composable<AppScreens.ResultScreen> { ResultScreen(navController) }
}

private fun NavGraphBuilder.imageSelector(navController: NavHostController) {
    composable<AppScreens.ImageSelectorScreen> { ImageSelectorScreen(navController) }
}