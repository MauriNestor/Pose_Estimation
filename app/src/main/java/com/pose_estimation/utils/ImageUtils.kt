package com.pose_estimation.utils

import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.media.Image
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

fun imageProxyToBitmap(imageProxy: ImageProxy): Bitmap {
    val image = imageProxy.image ?: return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Evitar null

    val yBuffer = image.planes[0].buffer // Y
    val uBuffer = image.planes[1].buffer // U
    val vBuffer = image.planes[2].buffer // V

    val ySize = yBuffer.remaining()
    val uSize = uBuffer.remaining()
    val vSize = vBuffer.remaining()

    val nv21 = ByteArray(ySize + uSize + vSize)

    // Copiar Y, U, V a un solo array
    yBuffer.get(nv21, 0, ySize)
    vBuffer.get(nv21, ySize, vSize)
    uBuffer.get(nv21, ySize + vSize, uSize)

    // Convertir NV21 a Bitmap
    val yuvImage = android.graphics.YuvImage(
        nv21,
        ImageFormat.NV21,
        image.width,
        image.height,
        null
    )

    val out = java.io.ByteArrayOutputStream()
    yuvImage.compressToJpeg(android.graphics.Rect(0, 0, image.width, image.height), 100, out)
    val imageBytes = out.toByteArray()

    val bitmap = android.graphics.BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    // Rotar la imagen si es necesario
    val matrix = Matrix().apply {
        postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
    }

    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
