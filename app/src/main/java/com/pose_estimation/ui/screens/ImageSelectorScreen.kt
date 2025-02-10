package com.pose_estimation.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.detector.models.Keypoint
import com.pose_estimation.utils.uriToBitmap

@Composable
fun ImageSelectorScreen(navController: NavHostController, poseDetector: PoseDetector) {
    AppScaffold {
        ImageSelectorScreenContent(navController, poseDetector)
    }

}

@Composable
fun ImageSelectorScreenContent(navController: NavHostController, poseDetector: PoseDetector) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var keypoints by remember { mutableStateOf<List<Keypoint>>(emptyList()) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
        uri?.let {
            val bitmap = context.uriToBitmap(it)
            selectedBitmap = bitmap
            keypoints = poseDetector.detectPose(bitmap)
        }
    }

    ImageSelectorContent(
        imageUri = imageUri,
        selectedBitmap = selectedBitmap,
        keypoints = keypoints,
        onImagePick = { imagePickerLauncher.launch("image/*") }
    )
}
@Composable
fun ImageSelectorContent(
    imageUri: Uri?,
    selectedBitmap: Bitmap?,
    keypoints: List<Keypoint>,
    onImagePick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onImagePick) {
            Text("Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedBitmap?.let { bitmap ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen procesada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                Canvas(modifier = Modifier.matchParentSize()) {
                    val (scale, offset) = calculateImageScaleAndOffset(
                        imageSize = Size(bitmap.width.toFloat(), bitmap.height.toFloat()),
                        canvasSize = size
                    )

                    keypoints.forEach { keypoint ->
                        if (keypoint.score > 0.5f) {
                            val scaledX = keypoint.x * scale.width + offset.x
                            val scaledY = keypoint.y * scale.height + offset.y

                            drawCircle(
                                color = Color.Red,
                                radius = 10f,
                                center = Offset(scaledX, scaledY)
                            )
                        }
                    }
                }

            }
        }
    }
}
fun calculateImageScaleAndOffset(imageSize: Size, canvasSize: Size): Pair<Size, Offset> {
    val scaleFactor = (canvasSize.width / imageSize.width).coerceAtMost(canvasSize.height / imageSize.height)
    val scaledSize = Size(imageSize.width * scaleFactor, imageSize.height * scaleFactor)
    val offset = Offset(
        (canvasSize.width - scaledSize.width) / 2,
        (canvasSize.height - scaledSize.height) / 2
    )
    return scaledSize to offset
}