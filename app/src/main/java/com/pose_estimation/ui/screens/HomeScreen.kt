package com.pose_estimation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pose_estimation.navigation.AppScreens

@Composable
fun HomeScreen(navController: NavHostController) {
    HomeScreenContent(onNavigate = { navController.navigate(AppScreens.ImageSelectorScreen.route) })
}

@Composable
fun HomeScreenContent(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a Pose Estimation")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigate) {
            Text("Ir a Selección de Imagen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(onNavigate = {})
}
