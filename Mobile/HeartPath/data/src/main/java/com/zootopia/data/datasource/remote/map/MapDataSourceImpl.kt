package com.zootopia.data.datasource.remote.map

import android.util.Log
import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.response.UncheckedLetterResponse
import com.zootopia.data.model.map.request.TmapWalkRoadRequest
import com.zootopia.data.model.map.response.navermap.MapDirectionResponse
import com.zootopia.data.model.map.response.tmap.FeatureCollectionResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.service.NaverService
import com.zootopia.data.service.TmapService
import com.zootopia.data.util.handleApi

private const val TAG = "MapDataSourceImpl_HP"

class MapDataSourceImpl(
    private val naverService: NaverService,
    private val tmapService: TmapService,

    private val businessService: BusinessService,
) : MapDataSource {
    /**
     * 네이버 맵 길찾기 요청
     */
    override suspend fun getNaverMapDirection(
        start: String,
        goal: String,
        option: String,
        apiKeyId: String,
        apiKey: String,
    ): MapDirectionResponse {
        return handleApi {
            naverService.getDrivingDirections(
                start,
                goal,
                option,
                apiKeyId,
                apiKey,
            )
        }
    }

    /**
     * tmap 길찾기 (도보)
     */
    override suspend fun requestTmapWalkRoad(
        tmapWalkRoadRequest: TmapWalkRoadRequest,
        appKey: String,
    ): FeatureCollectionResponse {
        return handleApi {
            Log.d(TAG, "requestTmapWalkRoad: 데이터소스에서 티맵 길 요청!!!!!")
            tmapService.getPedestrianRoute(
                requestData = tmapWalkRoadRequest,
                appKey = appKey,
            ).apply {
                Log.d(TAG, "requestTmapWalkRoad: $this")
            }
        }
    }

    /**
     * 미확인 편지 리스트 수신
     */
    override suspend fun getUncheckedLetter(): UncheckedLetterResponse {
        return handleApi {
            businessService.getUncheckedLetter()
        }
    }

    /**
     * 편지 줍기
     */
    override suspend fun getPickUpLetter(letter_id: Int): MessageResponse {
        return handleApi {
            businessService.getPickUpLetter(letter_id = letter_id)
        }
    }
}
