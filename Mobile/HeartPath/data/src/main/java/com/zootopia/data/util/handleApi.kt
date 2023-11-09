package com.zootopia.data.util

import android.util.Log
import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.util.getValueOrThrow
import retrofit2.Response

internal inline fun <T> handleApi(block: () -> Response<T>): T {
    return block.invoke().getValueOrThrow()
}