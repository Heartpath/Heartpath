package com.zootopia.data.mapper

import com.zootopia.data.model.letter.request.PostHandLetterRequest
import com.zootopia.data.model.letter.request.PostTypingLetterRequest
import com.zootopia.data.model.letter.response.GetUserLetterPaperResponse
import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.domain.model.writeletter.HandLetterRequestDto
import com.zootopia.domain.model.writeletter.TypingLetterRequestDto

fun HandLetterRequestDto.toData(): PostHandLetterRequest{
    return PostHandLetterRequest(
        receiverId = receiverId
    )
}

fun TypingLetterRequestDto.toData(): PostTypingLetterRequest{
    return PostTypingLetterRequest(
        receiverId = receiverId,
        text= text
    )
}

fun GetUserLetterPaperResponse.toDomain(): MutableList<UserLetterPaperDto>{
    return data
}