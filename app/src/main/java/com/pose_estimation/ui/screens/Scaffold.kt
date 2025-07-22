package com.pose_estimation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = { Text("APP ESTIMATION") }
        )
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    AppScaffold {
        Text("Contenido de ejemplo")
    }
}