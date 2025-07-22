package com.pose_estimation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pose_estimation.ui.navigation.AppNavigation
import com.pose_estimation.ui.navigation.AppScreens
import com.pose_estimation.ui.screens.AppScaffold
import com.pose_estimation.ui.theme.Pose_EstimationTheme

@Composable
fun PoseApp() {

    val appState = rememberPoseAppState()
    PoseScreen(navController = appState.navController) {
        AppNavigation(
            navController = appState.navController)
    }
}

@Composable
fun PoseScreen(navController: NavHostController, content: @Composable () -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val screenTitle = getTitleForRoute(currentRoute)

    Pose_EstimationTheme {
        AppScaffold(navController = navController, title = screenTitle) {
            content()
        }
    }
}

private fun getTitleForRoute(route: String?): String {
    return when (route) {
        AppScreens.HomeScreen.route -> "Inicio"
        AppScreens.ImageSelectorScreen.route -> "Selector de Imagen"
        AppScreens.ResultScreen.route -> "Resultado"
        AppScreens.UserProfileScreen.route -> "Perfil"
        AppScreens.SettingsScreen.route -> "Ajustes"
        else -> "Pose Estimation"
    }
}