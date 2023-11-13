package com.zootopia.data.repository.map

import android.util.Log
import com.zootopia.data.BuildConfig
import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.letter.uncheckedletter.UncheckLetterDto
import com.zootopia.domain.model.navermap.MapDirectionDto
import com.zootopia.domain.model.tmap.FeatureCollectionDto
import com.zootopia.domain.model.tmap.RequestTmapWalkRoadDto
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

private const val TAG = "MapRepositoryImpl_HP"

class MapRepositoryImpl @Inject constructor(
    val mapDataSource: MapDataSource,
) : MapRepository {
    val NAVER_MAP_CLIENT_ID = BuildConfig.DATA_NAVER_MAP_CLIENT_ID
    val NAVER_MAP_API_KEY = BuildConfig.DATA_NAVER_MAP_API_KEY
    val TMAP_APP_KEY = BuildConfig.TMAP_APP_KEY
    
    
    /**
     * 네이버 맵 길찾기 요청
     */
    override suspend fun requestMapDirection(
        start: String,
        goal: String,
        option: String,
    ): MapDirectionDto {
        Log.d(TAG, "requestMapDirection: ${NAVER_MAP_CLIENT_ID}")
        return getValueOrThrow2 {
            mapDataSource.getNaverMapDirection(
                start = start,
                goal = goal,
                option = option,
                apiKeyId = NAVER_MAP_CLIENT_ID,
                apiKey = NAVER_MAP_API_KEY
            ).toDomain()
        }
    }
    
    /**
     * tmap 길찾기 (도보)
     */
    override suspend fun requestTmapWalkRoad(
        requestTmapWalkRoadDto: RequestTmapWalkRoadDto,
    ): FeatureCollectionDto {
        return getValueOrThrow2 {
            Log.d(TAG, "requestTmapWalkRoad: 레파지토리에서 티맵 길 요청!!")
            mapDataSource.requestTmapWalkRoad(
                tmapWalkRoadRequest = requestTmapWalkRoadDto.toData(),
                appKey = TMAP_APP_KEY
            ).toDomain()
        }
    }
    
    /**
     * 미확인 편지 리스트 수신
     */
    override suspend fun getUncheckedLetter(): List<UncheckLetterDto> {
        return getValueOrThrow2 {
            mapDataSource.getUncheckedLetter().data as List<UncheckLetterDto>
        }
    }
    
    
}