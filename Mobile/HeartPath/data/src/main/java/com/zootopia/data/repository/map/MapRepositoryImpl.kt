package com.zootopia.data.repository.map

import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.map.MapDirectionDto
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    val mapDataSource: MapDataSource,
) : MapRepository {
    /**
     * 네이버 맵 길찾기 요청
     */
    override suspend fun requestMapDirection(
        start: String,
        goal: String,
        option: String,
    ): MapDirectionDto {
        return getValueOrThrow { mapDataSource.getNaverMapDirection(
            start = start,
            goal = goal,
            option = option,
            apiKeyId = "oetmwcozd1",
            apiKey = "7aiL7pVGQ93sYI6B3azANREgGdMVLDfQGthCEi0O"
        ).toDomain() }
    }
    
}