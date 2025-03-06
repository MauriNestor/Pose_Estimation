package com.pose_estimation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pose_estimation.ui.navigation.AppNavigation
import com.pose_estimation.ui.screens.BottomNavigationBar
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
    Pose_EstimationTheme {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}
