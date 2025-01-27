package com.pose_estimation.detector

import android.content.Context
import android.graphics.Bitmap
import com.pose_estimation.detector.models.Keypoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PoseDetector(context: Context) {
    private val interpreter: Interpreter
    private val inputSize = 256 // Tamaño requerido por Thunder

    init {
        // Cargar modelo desde assets
        val model = FileUtil.loadMappedFile(context, "movenet_thunder.tflite")
        interpreter = Interpreter(model)
    }

    fun detectPose(bitmap: Bitmap): List<Keypoint> {
        // 1. Redimensionar y convertir a Float32 (normalizado)
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(inputSize, inputSize, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0f, 255f)) // Normalizar de [0,255] a [0,1]
            .build()

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        val processedImage = imageProcessor.process(tensorImage)

        // 2. Ejecutar modelo
        val output = Array(1) { FloatArray(17 * 3) } // 17 keypoints * (y, x, score)
        interpreter.run(processedImage.buffer, output)

        return parseOutput(output[0])
    }

    private fun parseOutput(output: FloatArray): List<Keypoint> {
        val bodyParts = listOf(
            "nose", "left_eye", "right_eye", "left_ear", "right_ear",
            "left_shoulder", "right_shoulder", "left_elbow", "right_elbow",
            "left_wrist", "right_wrist", "left_hip", "right_hip",
            "left_knee", "right_knee", "left_ankle", "right_ankle"
        )

        return bodyParts.mapIndexed { index, part ->
            Keypoint(
                bodyPart = part,
                x = output[index * 3 + 1], // X está en la posición 1
                y = output[index * 3],     // Y está en la posición 0
                score = output[index * 3 + 2]
            )
        }
    }

    fun close() {
        interpreter.close()
    }
}


