package com.zootopia.domain.util

import android.util.Log
import org.json.JSONObject
import retrofit2.Response

private const val TAG = "NetworkUtil_HP"

private fun <T> Response<T>.isDelete(): Boolean {
    return this.raw().request.method == "DELETE"
}

// @Suppress("UNCHECKED_CAST")
fun <T> Response<T>.getValueOrThrow(): T {
    Log.d(TAG, "getValueOrThrow: $isSuccessful ${code()}")
    Log.d(TAG, "getValueOrThrow: ${this.body()}")
    if (this.isSuccessful) {
        if (this.isDelete()) { return Unit as T }
        Log.d(TAG, "getValueOrThrow: ${this.body()}")
        return this.body() ?: throw NetworkThrowable.IllegalStateThrowable()
    }

    Log.d(TAG, "getValueOrThrow: err code ${this.code()}")

    // TODO 서버에 따라 다를수도?
    val errorResponse = errorBody()?.string()
    val jsonObject = errorResponse?.let { JSONObject(it) }
    val status = jsonObject?.getInt("status") ?: 0
    val message = jsonObject?.getString("message") ?: ""

    Log.e(TAG, "getValueOrThrow: Error status : $status, message : $message")

    when (status) {
        in 100..199 -> { throw NetworkThrowable.Base100Throwable(status, message) }
        in 300..399 -> { throw NetworkThrowable.Base300Throwable(status, message) }
        in 400..499 -> { throw NetworkThrowable.Base400Throwable(status, message) }
        in 500..599 -> { throw NetworkThrowable.Base500Throwable(status, message) }
        in 4000..4050 -> { throw NetworkThrowable.Base40000Throwable(status, message) }
    }

    throw NetworkThrowable.IllegalStateThrowable()
}

/**
 * throwable 처리하기 전까지 가져갈 함수
 */
suspend fun <T> getValueOrThrow2(block: suspend () -> T): T {
    try {
        Log.d(TAG, "getValueOrThrow2 $block")
        return block()
    } catch (throwable: NetworkThrowable) {
        Log.d(TAG, "getValueOrThrow2: $throwable")
        throw throwable
    }
}
