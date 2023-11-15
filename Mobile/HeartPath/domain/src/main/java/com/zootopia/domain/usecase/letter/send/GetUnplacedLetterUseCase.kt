package com.zootopia.domain.usecase.letter.send

import com.zootopia.domain.model.letter.unplacedletter.UnPlacedLetterListDto
import com.zootopia.domain.repository.letter.SendLetterRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class GetUnplacedLetterUseCase @Inject constructor(
    private val sendLetterRepository: SendLetterRepository,
) {
    suspend operator fun invoke(): UnPlacedLetterListDto {
        return getValueOrThrow2 {
            sendLetterRepository.getUnplacedLetter()
        }
    }
}
