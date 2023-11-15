package com.zootopia.domain.usecase.letter.received

import com.zootopia.domain.repository.receiveletter.ReceiveLetterRepository
import javax.inject.Inject

class TestFcmUseCase @Inject constructor(
    private val receiveLetterRepository: ReceiveLetterRepository
) {
    suspend operator fun invoke(): String {
        return receiveLetterRepository.testFCM()
    }
}