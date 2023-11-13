package com.zootopia.domain.repository.map

import com.zootopia.domain.model.navermap.MapDirectionDto
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.domain.model.tmap.RequestTmapWalkRoadDto

interface MapRepository {
    /**
     * 네이버 맵 길찾기 요청
     */
    suspend fun requestMapDirection(
        start: String,
        goal: String,
        option: String,
    ): MapDirectionDto

    /**
     * tmap 길찾기 (도보)
     */
    suspend fun requestTmapWalkRoad(
        requestTmapWalkRoadDto: RequestTmapWalkRoadDto,
    ): FeatureCollectionDto

    // 테스트
    suspend fun test(): String
}
