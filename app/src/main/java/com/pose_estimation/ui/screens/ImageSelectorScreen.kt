package com.pose_estimation.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.pose_estimation.R
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.detector.models.Keypoint
import com.pose_estimation.utils.uriToBitmap
import java.io.File

@Composable
fun ImageSelectorScreen(navController: NavHostController) {
    AppScaffold {
        ImageSelectorScreenContent(navController)
    }

}

@Composable
fun ImageSelectorScreenContent(navController: NavHostController) {
    val context = LocalContext.current
    val poseDetector = PoseDetector(context)
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var keypoints by remember { mutableStateOf<List<Keypoint>>(emptyList()) }
    var isCameraActive by remember { mutableStateOf(false) }



    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
        uri?.let {
            val bitmap = context.uriToBitmap(it)
            selectedBitmap = bitmap
            keypoints = poseDetector.detectPose(bitmap)
            isCameraActive = false
        }
    }
    ImageSelectorContent(
        selectedBitmap = selectedBitmap,
        keypoints = keypoints,
        onImagePick = { imagePickerLauncher.launch("image/*") },
        onTakePhoto = { isCameraActive = true },
        isCameraActive = isCameraActive,
        onBitmapCaptured = { bitmap ->
            selectedBitmap = bitmap
            keypoints = poseDetector.detectPose(bitmap)
            isCameraActive = false
        }
    )
}
@Composable
fun ImageSelectorContent(
    selectedBitmap: Bitmap?,
    keypoints: List<Keypoint>,
    onImagePick: () -> Unit,
    onTakePhoto: () -> Unit,
    isCameraActive: Boolean,
    onBitmapCaptured: (Bitmap) -> Unit

) {
    val imageCapture = remember { mutableStateOf<ImageCapture?>(null) }
    val context = LocalContext.current

    val bodyConnections = listOf(
        Pair("nose", "left_eye"), Pair("nose", "right_eye"),
        Pair("left_eye", "left_ear"), Pair("right_eye", "right_ear"),
        Pair("left_shoulder", "right_shoulder"),
        Pair("left_shoulder", "left_elbow"), Pair("right_shoulder", "right_elbow"),
        Pair("left_elbow", "left_wrist"), Pair("right_elbow", "right_wrist"),
        Pair("left_shoulder", "left_hip"), Pair("right_shoulder", "right_hip"),
        Pair("left_hip", "right_hip"),
        Pair("left_hip", "left_knee"), Pair("right_hip", "right_knee"),
        Pair("left_knee", "left_ankle"), Pair("right_knee", "right_ankle")
    )

    Column(

        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onImagePick) {
            Text("Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onTakePhoto) {
            Text("Tomar Foto")
        }
        if (isCameraActive) {
            CameraPreview(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                imageCapture = imageCapture,
                onImageCaptured = { uri ->
                    val bitmap = context.uriToBitmap(uri)
                    onBitmapCaptured(bitmap)
                }
            )

            Image(
                painter = painterResource(id = R.drawable.boceto_persona),
                contentDescription = "Guía de posición",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1000.dp)
            )
        }

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
                    bodyConnections.forEach { (startPart, endPart) ->
                        val start = keypoints.find { it.bodyPart == startPart && it.score > 0.5f }
                        val end = keypoints.find { it.bodyPart == endPart && it.score > 0.5f }

                        if (start != null && end != null) {
                            val startX = start.x * scale.width + offset.x
                            val startY = start.y * scale.height + offset.y
                            val endX = end.x * scale.width + offset.x
                            val endY = end.y * scale.height + offset.y

                            drawLine(
                                color = Color.Blue,
                                start = Offset(startX, startY),
                                end = Offset(endX, endY),
                                strokeWidth = 4f
                            )
                        }
                    }
                }


            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    imageCapture: MutableState<ImageCapture?>,
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                val previewView = androidx.camera.view.PreviewView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val imageCaptureConfig = ImageCapture.Builder().build()
                    imageCapture.value = imageCaptureConfig

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCaptureConfig)
                    } catch (e: Exception) {
                        Log.e("CameraPreview", "Error al iniciar la cámara", e)
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        FloatingActionButton(
            onClick = { takePhoto(context, imageCapture.value, onImageCaptured) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            containerColor = Color.White
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "Tomar foto", tint = Color.Black)
        }
    }
}

private fun takePhoto(context: Context, imageCapture: ImageCapture?, onImageCaptured: (Uri) -> Unit) {
    val file = File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

    imageCapture?.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val uri = Uri.fromFile(file)
                onImageCaptured(uri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraPreview", "Error al capturar la imagen", exception)
            }
        }
    )
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