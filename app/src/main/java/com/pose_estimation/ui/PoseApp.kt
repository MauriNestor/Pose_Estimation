package com.pose_estimation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

// PoseScreen.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoseScreen(navController: NavHostController, content: @Composable () -> Unit) {
    Pose_EstimationTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("APP ESTIMATION") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    )
                )
            },
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}

