package com.zootopia.data.repository.map

import android.util.Log
import com.zootopia.data.BuildConfig
import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.map.MapDirectionDto
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow
import javax.inject.Inject

private const val TAG = "MapRepositoryImpl_HP"
class MapRepositoryImpl @Inject constructor(
    val mapDataSource: MapDataSource,
) : MapRepository {
    val NAVER_MAP_CLIENT_ID = BuildConfig.DATA_NAVER_MAP_CLIENT_ID
    val NAVER_MAP_API_KEY = BuildConfig.DATA_NAVER_MAP_API_KEY
    
    /**
     * 네이버 맵 길찾기 요청
     */
    override suspend fun requestMapDirection(
        start: String,
        goal: String,
        option: String,
    ): MapDirectionDto {
        Log.d(TAG, "requestMapDirection: ${NAVER_MAP_CLIENT_ID}")
        return getValueOrThrow { mapDataSource.getNaverMapDirection(
            start = start,
            goal = goal,
            option = option,
            apiKeyId = NAVER_MAP_CLIENT_ID,
            apiKey = NAVER_MAP_API_KEY
        ).toDomain() }
    }
    
}