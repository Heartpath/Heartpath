package com.zootopia.domain.usecase.writeletter

import com.zootopia.domain.model.writeletter.HandLetterRequestDto
import com.zootopia.domain.repository.business.BusinessRepository
import javax.inject.Inject

class PostHandLetterUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {
    suspend operator fun invoke(
        handLetterRequestDto: HandLetterRequestDto,
        content: String,
        fileList: MutableList<String>
    ) {
        businessRepository.postHandWriteLetter(handLetterRequestDto, content, fileList)
    }
}