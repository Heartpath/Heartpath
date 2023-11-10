package com.zootopia.domain.repository.letter

import com.zootopia.domain.model.unplacedletter.UnPlacedLetterListDto

interface SendLetterRepository {
    suspend fun getUnplacedLetter(): UnPlacedLetterListDto
}