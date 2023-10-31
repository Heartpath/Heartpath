package com.zootopia.domain.usecase.map

import com.zootopia.domain.model.map.MapDirectionDto
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow
import javax.inject.Inject

class GetMapDirectionUseCase @Inject constructor(
    private val mapRepository: MapRepository,
) {
    suspend operator fun invoke(
        start: String,
        goal: String,
        option: String,
    ): MapDirectionDto {
        return getValueOrThrow {
            mapRepository.requestMapDirection(
                start = start,
                goal = goal,
                option = option,
            )
        }
    }
}