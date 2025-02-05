package com.pose_estimation.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.navigation.AppNavigation
import com.pose_estimation.ui.theme.Pose_EstimationTheme

@Composable
fun PoseApp() {
    val context = LocalContext.current
    val poseDetector = PoseDetector(context)

    val appState = rememberPoseAppState()
    PoseScreen {
        AppNavigation(
            navController = appState.navController,
            poseDetector = poseDetector
        )
    }
}

@Composable
fun PoseScreen(content: @Composable () -> Unit) {
    Pose_EstimationTheme {
        Surface {
            content()
        }
    }
}
