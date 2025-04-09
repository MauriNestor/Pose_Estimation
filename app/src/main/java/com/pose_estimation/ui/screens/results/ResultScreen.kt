package com.pose_estimation.ui.screens.results

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pose_estimation.R
import com.pose_estimation.ui.screens.AppScaffold

@Composable
fun ResultScreen(navController: NavHostController) {
    AppScaffold {
        ResultScreenContent()
    }
}

@Composable
fun ResultScreenContent() {
    ResultContent()
}

@Composable
fun ResultContent() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Más nuevo primero", "Más antiguo primero")

    Column {
        // Tabs
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        // Lista de elementos
        val items = remember { mutableStateListOf(
            ItemData("Análisis Rápido", "Frente", "1.2 MB", "20.1.2025 21:32", R.drawable.postura)
        ) }

        LazyColumn {
            items(items) { item ->
                ItemRow(item, onDelete = { items.remove(item) })
            }
        }
    }
}

// Modelo de datos
data class ItemData(
    val title: String,
    val subtitle: String,
    val size: String,
    val date: String,
    @DrawableRes val imageRes: Int
)

// Elemento de la lista
@Composable
fun ItemRow(item: ItemData, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = item.subtitle, color = Color.Gray)
            Text(text = item.size, color = Color.Gray, fontSize = 12.sp)
        }

        Text(text = item.date, color = Color.Gray, fontSize = 12.sp)

        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewResultScreen() {
    ResultScreenContent()
}
