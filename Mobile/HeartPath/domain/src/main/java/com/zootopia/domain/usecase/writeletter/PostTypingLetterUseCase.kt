package com.zootopia.domain.usecase.writeletter

import com.zootopia.domain.model.writeletter.TypingLetterRequestDto
import com.zootopia.domain.repository.letter.WriteLetterRepository
import javax.inject.Inject

class PostTypingLetterUseCase @Inject constructor(
    private val businessRepository: WriteLetterRepository
) {
    suspend operator fun invoke(
        typingLetterRequestDto: TypingLetterRequestDto,
        content: String,
        fileList: MutableList<String>
    ) {
        businessRepository.postTypingWriteLetter(typingLetterRequestDto, content, fileList)
    }
}