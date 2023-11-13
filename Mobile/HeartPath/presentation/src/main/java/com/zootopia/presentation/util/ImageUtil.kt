package com.zootopia.presentation.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.SurfaceTexture
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.Surface
import android.view.View
import androidx.core.net.toUri
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

fun getImages(context: Context): MutableList<Uri> {
    Log.d(TAG, "getImages: ~~~~~~~~~~~~~~~~~~~~~~~")
    var list = mutableListOf<Uri>()

    // 가져올 이미지의 속성을 지정. 여기서는 이미지 ID, 이미지 파일 이름, 이미지 파일 경로를 가져옴
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.DATA,
    )

    // 이미지를 추가된 날짜의 역순으로 정렬하여 최신 이미지부터 가져옴
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 외부 저장소의 이미지 컨텐츠를 가리키는 URI를 사용하여 이미지 정보를 가져옴
        projection,
        null,
        null,
        sortOrder,
    )

    // 커서로 앨범의 이미지를 하나씩 addToGalleryList에 저장
    cursor?.use { cursor ->
        val idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val nameColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
        val dataColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

        while (cursor.moveToNext()) {
            val imageId = cursor.getLong(idColumnIndex)
            val imageName = cursor.getString(nameColumnIndex)
            val imageData = cursor.getString(dataColumnIndex)
            // 이미지 데이터를 사용하여 필요한 작업 수행
            list.add(
                imageData.toUri(),
            )
        }
    }

    return list
}

// ARSceneView의 OpenGL 컨텐츠 캡처 함수
fun loadBitmapFromView(view: View): Bitmap {
    // 뷰와 동일한 크기의 비트맵 생성
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)

    // SurfaceTexture를 이용하여 Surface 생성
    val surfaceTexture = SurfaceTexture(0)
    val surface = Surface(surfaceTexture)

    // Surface에 뷰를 그려서 비트맵에 복사
    val canvas = surface.lockCanvas(null)
    view.draw(canvas)
    surface.unlockCanvasAndPost(canvas)

    // Surface 및 SurfaceTexture 해제
    surface.release()
    surfaceTexture.release()

    return bitmap
}


