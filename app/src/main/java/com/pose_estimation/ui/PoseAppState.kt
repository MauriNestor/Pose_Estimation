package com.pose_estimation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberPoseAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): PoseAppState = remember(navController, coroutineScope){
    PoseAppState(navController, coroutineScope)
}

class PoseAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
)