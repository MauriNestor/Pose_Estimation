package com.pose_estimation.ui.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home")
    object ResultScreen : AppScreens("result")
    object UserProfileScreen : AppScreens("userProfile")
    object SettingsScreen : AppScreens("settings")
    object ImageSelectorScreen : AppScreens("imageSelector")
//    @Serializable @SerialName("result") object ResultScreen : AppScreens()

}