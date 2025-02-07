package com.pose_estimation.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.pose_estimation.navigation.AppScreens
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        AppScreens.HomeScreen,
        AppScreens.ImageSelectorScreen,
        AppScreens.PDFScreen
    )

    NavigationBar { // Cambiado de BottomNavigation a NavigationBar
        val currentRoute = navController.currentDestination?.route
        items.forEach { screen ->
            NavigationBarItem( // Cambiado de BottomNavigationItem a NavigationBarItem
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            AppScreens.HomeScreen -> Icons.Filled.Home
                            AppScreens.ImageSelectorScreen -> Icons.Filled.Done
                            AppScreens.PDFScreen -> Icons.Filled.Build
                        },
                        contentDescription = screen.route
                    )
                },
                label = { Text(screen.route) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}
