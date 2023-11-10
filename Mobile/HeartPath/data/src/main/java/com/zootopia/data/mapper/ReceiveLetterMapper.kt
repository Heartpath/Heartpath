package com.zootopia.data.mapper

import com.zootopia.data.model.letter.response.StoredLetterListResponse
import com.zootopia.domain.model.letter.ReceiveLetterDto

fun StoredLetterListResponse.toDomain(): List<ReceiveLetterDto>? {
    return data
}
