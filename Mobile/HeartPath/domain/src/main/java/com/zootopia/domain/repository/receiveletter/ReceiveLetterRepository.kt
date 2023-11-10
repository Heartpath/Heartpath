package com.zootopia.domain.repository.receiveletter

import com.zootopia.domain.model.letter.ReceiveLetterDto

interface ReceiveLetterRepository {
    suspend fun getStoredLetterList(): List<ReceiveLetterDto>?
}