package com.zootopia.data.mapper

import com.zootopia.data.model.common.MessageResponse

fun MessageResponse.toDomain(): String {
    return message
}
