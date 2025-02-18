package com.pose_estimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pose_estimation.ui.PoseApp

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
