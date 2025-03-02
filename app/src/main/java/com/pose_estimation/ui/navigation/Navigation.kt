package com.pose_estimation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.ui.screens.ImageSelectorScreen
import com.pose_estimation.ui.screens.home.HomeScreen
import com.pose_estimation.ui.screens.PDFScreen
import com.pose_estimation.ui.screens.SettingsScreen
import com.pose_estimation.ui.screens.UserProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    poseDetector: PoseDetector
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen
    ){
        home(navController = navController)
        pdfScreen(navController = navController)
        userProfile(navController = navController)
        settings(navController = navController)
        imageSelector(navController = navController, poseDetector)
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

private fun NavGraphBuilder.pdfScreen(navController: NavHostController) {
    composable<AppScreens.PDFScreen> { PDFScreen(navController) }
}

private fun NavGraphBuilder.imageSelector(navController: NavHostController, poseDetector: PoseDetector) {
    composable<AppScreens.ImageSelectorScreen> { ImageSelectorScreen(navController, poseDetector) }
}