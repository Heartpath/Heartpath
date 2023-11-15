package com.zootopia.domain.usecase.map

import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class GetUncheckedLetterUseCase @Inject constructor(
    private val mapRepository: MapRepository
){
    suspend operator fun invoke(): List<UncheckLetterDto> {
        return getValueOrThrow2 {
            mapRepository.getUncheckedLetter()
        }
    }
}