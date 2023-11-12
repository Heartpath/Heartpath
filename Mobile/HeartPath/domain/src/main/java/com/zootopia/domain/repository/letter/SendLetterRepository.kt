package com.zootopia.domain.repository.letter

import com.zootopia.domain.model.letter.sendletter.LetterPlacedDto
import com.zootopia.domain.model.letter.unplacedletter.UnPlacedLetterListDto
import okhttp3.MultipartBody

interface SendLetterRepository {
    suspend fun getUnplacedLetter(): UnPlacedLetterListDto

    suspend fun requestLetterPlaced(
        files: MultipartBody.Part,
        letterPlacedDto: LetterPlacedDto,
    ): String
}
