package com.pose_estimation.detector.models

data class Keypoint(

    var bodyPart: String,
    val x : Float,
    val y : Float,
    val score: Float
)
