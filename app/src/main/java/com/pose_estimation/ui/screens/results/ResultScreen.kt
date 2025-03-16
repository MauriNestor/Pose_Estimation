package com.pose_estimation.ui.screens.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.pose_estimation.ui.screens.AppScaffold

@Composable
fun PDFScreen(navController: NavHostController) {
    AppScaffold {
        PDFScreenContent()
    }
}

@Composable
fun PDFScreenContent() {
    PDFContent()
}

@Composable
fun PDFContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Gestor de PDFs")
    }
}