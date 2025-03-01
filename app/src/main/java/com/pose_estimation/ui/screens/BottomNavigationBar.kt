package com.pose_estimation.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.pose_estimation.navigation.AppScreens
import com.pose_estimation.ui.theme.Pose_EstimationTheme

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        AppScreens.HomeScreen,
        AppScreens.PDFScreen,
        AppScreens.SettingsScreen,
        AppScreens.UserProfileScreen
    )

    val screenIcons = mapOf(
        AppScreens.HomeScreen to Icons.Filled.Home,
        AppScreens.PDFScreen to Icons.Filled.AccountBox,
        AppScreens.SettingsScreen to Icons.Filled.Settings,
        AppScreens.UserProfileScreen to Icons.Filled.Person
    )

    val screenLabels = mapOf(
        AppScreens.HomeScreen to "Inicio",
        AppScreens.PDFScreen to "Archivos",
        AppScreens.SettingsScreen to "Ajustes",
        AppScreens.UserProfileScreen to "Perfil"
    )

    NavigationBar {
        val currentRoute = navController.currentDestination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screenIcons[screen] ?: Icons.Filled.Home, contentDescription = screenLabels[screen]) },
                label = { Text(screenLabels[screen] ?: "Pantalla") },
                selected = currentRoute == screen.toString(),
                onClick = {
                    if (currentRoute != screen.toString()) {
                        navController.navigate(screen)
                    }
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    Pose_EstimationTheme {
        NavigationBar {
            listOf(
                Icons.Filled.Home to "Inicio",
                Icons.Filled.AccountBox to "Archivos",
                Icons.Filled.Settings to "Ajustes",
                Icons.Filled.Person to "Perfil"
            ).forEach { (icon, label) ->
                NavigationBarItem(
                    icon = { Icon(imageVector = icon, contentDescription = label) },
                    label = { Text(label) },
                    selected = false,
                    onClick = { /* No-op en preview */ }
                )
            }
        }
    }
}
