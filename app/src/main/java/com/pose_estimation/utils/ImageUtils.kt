package com.pose_estimation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun Context.uriToBitmap(uri: Uri): Bitmap {
    val originalBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source = ImageDecoder.createSource(contentResolver, uri)
        ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
            decoder.setAllocator(ImageDecoder.ALLOCATOR_SOFTWARE) // Evita formatos incompatibles
        }
    } else {
        MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }

    // Convertir a ARGB_8888 si no está en ese formato
    return originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
}