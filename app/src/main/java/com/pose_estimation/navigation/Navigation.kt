package com.pose_estimation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.ui.screens.ImageSelectorScreen
import com.pose_estimation.ui.screens.HomeScreen
import com.pose_estimation.ui.screens.PDFScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    poseDetector: PoseDetector
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route
    ){
        home(navController = navController)
        imageSelector(navController, poseDetector)
        pdfScreen(navController)

    }
}

private fun NavGraphBuilder.home(navController: NavHostController){
    composable(AppScreens.HomeScreen.route) {
        HomeScreen(navController)
    }
}

private fun NavGraphBuilder.imageSelector(navController: NavHostController, poseDetector: PoseDetector) {
    composable(AppScreens.ImageSelectorScreen.route) {
        ImageSelectorScreen(navController, poseDetector)
    }
}
private fun NavGraphBuilder.pdfScreen(navController: NavHostController) {
    composable(AppScreens.PDFScreen.route) {
        PDFScreen(navController)
    }
}