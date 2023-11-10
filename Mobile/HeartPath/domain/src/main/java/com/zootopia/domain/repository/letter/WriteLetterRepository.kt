package com.zootopia.domain.repository.letter

import com.zootopia.domain.model.writeletter.HandLetterRequestDto

interface WriteLetterRepository {
    suspend fun postHandWriteLetter(
        handLetterRequestDto: HandLetterRequestDto,
        content: String,
        fileList: MutableList<String>
    )
}