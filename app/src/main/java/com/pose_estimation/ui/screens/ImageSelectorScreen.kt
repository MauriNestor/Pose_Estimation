package com.pose_estimation.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.detector.models.Keypoint
import com.pose_estimation.utils.uriToBitmap
import coil.compose.rememberAsyncImagePainter
import com.pose_estimation.navigation.AppScreens


@Composable
fun ImageSelectorScreen(navController: NavController, poseDetector: PoseDetector) {
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate(AppScreens.HomeScreen.route) }) {
            Text("Volver al Inicio")
        }

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }

        selectedBitmap?.let { bitmap ->
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Imagen procesada")
                DrawPoseOverlay(keypoints)
            }
        }
    }
}
