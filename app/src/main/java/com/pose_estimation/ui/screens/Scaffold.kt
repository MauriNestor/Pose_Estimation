package com.pose_estimation.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("APP ESTIMATION") }
            )
        },
        content = { paddingValues ->
            content()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    AppScaffold {
        Text("Contenido de ejemplo")
    }
}