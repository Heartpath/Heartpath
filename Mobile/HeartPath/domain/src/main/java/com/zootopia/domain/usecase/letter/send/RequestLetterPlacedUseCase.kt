package com.zootopia.domain.usecase.letter.send

import com.zootopia.domain.model.letter.sendletter.LetterPlacedDto
import com.zootopia.domain.repository.letter.SendLetterRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class RequestLetterPlacedUseCase @Inject constructor(
    private val sendLetterRepository: SendLetterRepository,
) {
    suspend operator fun invoke(
        files: String,
        letterPlacedDto: LetterPlacedDto,
    ): String {
        return getValueOrThrow2 {
            sendLetterRepository.requestLetterPlaced(
                files = files,
                letterPlacedDto = letterPlacedDto,
            )
        }
    }
}
