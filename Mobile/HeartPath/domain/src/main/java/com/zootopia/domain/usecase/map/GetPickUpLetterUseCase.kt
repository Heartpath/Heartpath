package com.zootopia.domain.usecase.map

import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class GetPickUpLetterUseCase @Inject constructor(
    private val mapRepository: MapRepository,
) {
    suspend operator fun invoke(letter_id: Int): String {
        return getValueOrThrow2 {
            mapRepository.getPickUpLetter(letter_id = letter_id)
        }
    }
}
