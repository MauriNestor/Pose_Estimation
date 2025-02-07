package com.pose_estimation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.navigation.AppNavigation
import com.pose_estimation.ui.screens.BottomNavigationBar
import com.pose_estimation.ui.theme.Pose_EstimationTheme

@Composable
fun PoseApp() {
    val context = LocalContext.current
    val poseDetector = PoseDetector(context)

    val appState = rememberPoseAppState()
    PoseScreen(navController = appState.navController) {
        AppNavigation(
            navController = appState.navController,
            poseDetector = poseDetector
        )
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
