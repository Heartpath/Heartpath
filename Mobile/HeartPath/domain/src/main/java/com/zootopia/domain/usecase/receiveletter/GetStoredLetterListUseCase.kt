package com.zootopia.domain.usecase.receiveletter

import com.zootopia.domain.model.letter.ReceiveLetterDto
import com.zootopia.domain.repository.receiveletter.ReceiveLetterRepository
import javax.inject.Inject

class GetStoredLetterListUseCase @Inject constructor(
    private val receiveLetterRepository: ReceiveLetterRepository
) {
    suspend operator fun invoke(): List<ReceiveLetterDto>? {
        return receiveLetterRepository.getStoredLetterList()
    }

}