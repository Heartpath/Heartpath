package com.zootopia.domain.usecase.writeletter

import com.zootopia.domain.model.letter.UserLetterPaperDto
import com.zootopia.domain.repository.letter.WriteLetterRepository
import javax.inject.Inject

class GetUserLetterPaperUseCase @Inject constructor(
    private val writeLetterRepository: WriteLetterRepository
){
    suspend operator fun invoke(): MutableList<UserLetterPaperDto>{
        return writeLetterRepository.getUserLetterPaper()
    }
}