package com.zootopia.data.util

import com.zootopia.domain.util.getValueOrThrow
import retrofit2.Response

internal inline fun <T> handleApi(block: () -> Response<T>): T {
    return block.invoke().getValueOrThrow()
}
