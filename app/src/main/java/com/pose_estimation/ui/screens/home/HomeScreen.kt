package com.pose_estimation.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.pose_estimation.navigation.AppScreens
import com.pose_estimation.ui.screens.AppScaffold

@Composable
fun HomeScreen(navController: NavHostController) {
    AppScaffold {
        HomeScreenContent(onNavigate = {
            navController.navigate(AppScreens.ImageSelectorScreen)
        })
    }
}

@Composable
fun HomeScreenContent(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(top = 56.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeatureCard(
            title = "Análisis rápido",
            imageUrl = "android.resource://com.pose_estimation/drawable/postura",
            backgroundColor = Color.White,
            onClick = { onNavigate() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FeatureCard(
            title = "Ejercicios Recomendados",
            imageUrl = "android.resource://com.pose_estimation/drawable/postura",
            backgroundColor = Color.White,
            onClick = { /* Futuro: navController.navigate(AppScreens.ExercisesScreen) */ }
        )

    }
}
@Composable
fun FeatureCard(title: String, imageUrl: String, backgroundColor: Color, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagen de $title",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Título
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                modifier = Modifier.weight(1f)
                    .padding(bottom = 25.dp)
            )

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFF1E1E2D), shape = CircleShape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir a $title",
                    tint = Color.White
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(onNavigate = {})
}
