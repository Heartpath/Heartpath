package com.zootopia.domain.usecase.letter.received

import com.zootopia.domain.model.letter.ReadLetterDto
import com.zootopia.domain.repository.receiveletter.ReceiveLetterRepository
import javax.inject.Inject

class GetLetterToReadUseCase @Inject constructor(
    private val receiveLetterRepository: ReceiveLetterRepository
) {
    suspend operator fun invoke(letterId: Int): ReadLetterDto? {
        return receiveLetterRepository.getLetterToRead(id = letterId)
    }
}