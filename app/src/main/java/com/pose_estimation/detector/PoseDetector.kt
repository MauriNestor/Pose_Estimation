package com.pose_estimation.detector

import android.content.Context
import android.graphics.Bitmap
import com.pose_estimation.detector.models.Keypoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PoseDetector(context: Context) {
    private lateinit var interpreter: Interpreter
    private lateinit var inputTensor: ByteBuffer
    private val inputSize = 256

    init {
        val modelFile = FileUtil.loadMappedFile(context, "movenet_thunder.tflite")
        interpreter = Interpreter(modelFile)
        inputTensor = ByteBuffer.allocateDirect(inputSize * inputSize * 3)
        inputTensor.order(ByteOrder.nativeOrder())
    }

    fun detectPose(bitmap: Bitmap): List<Keypoint> {
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.BILINEAR))
            .build()
        val tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)
        val processedImage = imageProcessor.process(tensorImage)

        val outputShape = arrayOf(1, 1, 17, 3)
        val outputBuffer = Array(outputShape[0]) { Array(outputShape[1]) { Array(outputShape[2]) { FloatArray(outputShape[3]) } } }

        interpreter.run(processedImage.buffer, outputBuffer)

        return parseOutput(outputBuffer[0][0])
    }

    private fun parseOutput(output: Array<FloatArray>): List<Keypoint> {
        val keypoints = mutableListOf<Keypoint>()
        val bodyParts = listOf(
            "nose", "left_eye", "right_eye", "left_ear", "right_ear",
            "left_shoulder", "right_shoulder", "left_elbow", "right_elbow",
            "left_wrist", "right_wrist", "left_hip", "right_hip",
            "left_knee", "right_knee", "left_ankle", "right_ankle"
        )

        for (i in bodyParts.indices) {
            val y = output[i][0]
            val x = output[i][1]
            val score = output[i][2]
            keypoints.add(Keypoint(bodyPart = bodyParts[i], x = x, y = y, score = score))
        }
        return keypoints
    }

    fun close() {
        interpreter.close()
    }
}

