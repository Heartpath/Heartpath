package com.zootopia.domain.repository.receiveletter

import com.zootopia.domain.model.letter.ReadLetterDto
import com.zootopia.domain.model.letter.ReceiveLetterDto

interface ReceiveLetterRepository {
    suspend fun getStoredLetterList(): List<ReceiveLetterDto>?
    suspend fun getLetterToRead(id: Int): ReadLetterDto?
}