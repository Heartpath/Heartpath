package com.zootopia.domain.usecase

import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class testUseCase @Inject constructor(
    private val mapRepository: MapRepository
){
    suspend operator fun invoke(): String {
        return getValueOrThrow2 { mapRepository.test() }
    }
}