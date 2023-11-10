package com.zootopia.data.mapper

import com.zootopia.data.model.letter.request.PostHandLetterRequest
import com.zootopia.data.model.letter.response.GetUserLetterPaperResponse
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.domain.model.writeletter.HandLetterRequestDto

fun HandLetterRequestDto.toData(): PostHandLetterRequest{
    return PostHandLetterRequest(
        receiverId = receiverId
    )
}

fun GetUserLetterPaperResponse.toDomain(): MutableList<UserLetterPaperDto>{
    return data
}