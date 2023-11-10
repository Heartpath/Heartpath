package com.zootopia.presentation.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.zootopia.presentation.config.BaseViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

private const val TAG = "ImageUtil"

fun getRealPathFromUri(context: Context, contentUri: Uri): String? {
    var result: String? = null
    context.contentResolver.openInputStream(contentUri)?.use { stream ->
        val tempFile = File(context.cacheDir, "${UUID.randomUUID()}_${SimpleDateFormat("yyMMdd_HHmmss").format(Date())}.jpg")
        val outputStream = FileOutputStream(tempFile)
        stream.copyTo(outputStream)
        result = tempFile.absolutePath
    }
    return result
}

fun saveImageToGallery(context: Context, bitmap: Bitmap): Uri? {
    val contentResolver: ContentResolver = context.contentResolver
    val displayName = "${UUID.randomUUID()}_${SimpleDateFormat("yyMMdd_HHmmss").format(Date())}"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, bitmap.width)
        put(MediaStore.Images.Media.HEIGHT, bitmap.height)
    }

    var outputStream: OutputStream? = null
    try {
        val collection =
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val imageUri = contentResolver.insert(collection, contentValues)
        if (imageUri != null) {
            outputStream = contentResolver.openOutputStream(imageUri)
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                return imageUri
            }
        }
    } catch (e: Exception) {
        // 저장 실패 시 예외 처리
        e.printStackTrace()
    } finally {
        outputStream?.close()
    }
    return null
}

// view의 bitmap을 따서 반환한다
fun viewToBitmap(view: View): Bitmap {
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

