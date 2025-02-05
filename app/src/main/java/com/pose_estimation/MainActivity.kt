package com.pose_estimation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.pose_estimation.detector.PoseDetector
import com.pose_estimation.navigation.AppScreens
import com.pose_estimation.ui.PoseApp
import com.pose_estimation.ui.screens.HomeScreen
import com.pose_estimation.ui.screens.ImageSelectorScreen
import com.pose_estimation.ui.theme.Pose_EstimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoseApp()
        }
    }
}

//        val testBitmap = Bitmap.createScaledBitmap(
//            BitmapFactory.decodeResource(resources, R.drawable.test_posture),
//            256, 256, true
//        )
//
//        val keypoints = poseDetector.detectPose(testBitmap)
//        keypoints.forEach {
//            Log.d("PoseDebug", "${it.bodyPart}: (${it.x}, ${it.y}) - Score: ${it.score}")
//        }
