package com.zootopia.domain.repository.business

import com.zootopia.domain.model.writeletter.HandLetterRequestDto

interface BusinessRepository {
    suspend fun postHandWriteLetter(
        handLetterRequestDto: HandLetterRequestDto,
        content: String,
        fileList: MutableList<String>
    )
}