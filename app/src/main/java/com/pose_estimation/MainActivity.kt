package com.pose_estimation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.ui.CameraScreen
import com.pose_estimation.ui.theme.Pose_EstimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val poseDetector = PoseDetector(applicationContext)
        setContent {
            Pose_EstimationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CameraScreen(poseDetector) // 🔥 Llamamos a CameraScreen aquí

                }
            }
        }

        val testBitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.test_posture),
            256, 256, true
        )

        val keypoints = poseDetector.detectPose(testBitmap)
        keypoints.forEach {
            Log.d("PoseDebug", "${it.bodyPart}: (${it.x}, ${it.y}) - Score: ${it.score}")
        }
    }
}

