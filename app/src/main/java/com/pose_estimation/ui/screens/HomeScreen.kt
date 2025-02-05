package com.pose_estimation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pose_estimation.navigation.AppScreens

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a Pose Estimation")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate(AppScreens.ImageSelectorScreen.route) }) {
            Text("Ir a Selección de Imagen")
        }
    }
}
