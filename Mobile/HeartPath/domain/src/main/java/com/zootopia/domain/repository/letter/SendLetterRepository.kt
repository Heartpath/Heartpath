package com.zootopia.domain.repository.letter

import com.zootopia.domain.model.letter.sendletter.LetterPlacedDto
import com.zootopia.domain.model.letter.unplacedletter.UnPlacedLetterListDto

interface SendLetterRepository {
    suspend fun getUnplacedLetter(): UnPlacedLetterListDto

    suspend fun requestLetterPlaced(
        files: String,
        letterPlacedDto: LetterPlacedDto,
    ): String
}
