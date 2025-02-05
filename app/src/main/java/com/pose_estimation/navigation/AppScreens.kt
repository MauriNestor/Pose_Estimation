package com.pose_estimation.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home")
    object ImageSelectorScreen : AppScreens("image_selector")
}