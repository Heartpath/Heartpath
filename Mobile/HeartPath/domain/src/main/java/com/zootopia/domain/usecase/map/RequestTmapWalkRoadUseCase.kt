package com.zootopia.domain.usecase.map

import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.domain.model.tmap.RequestTmapWalkRoadDto
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class RequestTmapWalkRoadUseCase @Inject constructor(
    private val mapRepository: MapRepository
){
    suspend operator fun invoke(
        requestTmapWalkRoadDto: RequestTmapWalkRoadDto
    ): FeatureCollectionDto {
        return getValueOrThrow2 {
            mapRepository.requestTmapWalkRoad(requestTmapWalkRoadDto = requestTmapWalkRoadDto)
        }
    }
}