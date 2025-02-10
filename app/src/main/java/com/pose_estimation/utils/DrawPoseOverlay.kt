package com.pose_estimation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import com.pose_estimation.detector.models.Keypoint

@Composable
fun DrawPoseOverlay(keypoints: List<Keypoint>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        keypoints.forEach { keypoint ->
            if (keypoint.score > 0.5f) { // Filtrar puntos con baja confianza
                drawCircle(
                    color = Color.Red,
                    radius = 10f,
                    center = Offset(keypoint.x * size.width, keypoint.y * size.height)
                )
            }
        }
    }
}
