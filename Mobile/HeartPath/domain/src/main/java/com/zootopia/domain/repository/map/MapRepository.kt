package com.zootopia.domain.repository.map

import com.zootopia.domain.model.map.MapDirectionDto

interface MapRepository {
    /**
     * 네이버 맵 길찾기 요청
     */
    suspend fun requestMapDirection(
        start: String,
        goal: String,
        option: String,
    ): MapDirectionDto
}