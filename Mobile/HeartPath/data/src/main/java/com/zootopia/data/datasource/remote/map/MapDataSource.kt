package com.zootopia.data.datasource.remote.map

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.response.UncheckedLetterResponse
import com.zootopia.data.model.map.request.TmapWalkRoadRequest
import com.zootopia.data.model.map.response.navermap.MapDirectionResponse
import com.zootopia.data.model.map.response.tmap.FeatureCollectionResponse

interface MapDataSource {
    /**
     * 네이버 맵 길찾기 요청
     */
    suspend fun getNaverMapDirection(
        start: String,
        goal: String,
        option: String,
        apiKeyId: String,
        apiKey: String,
    ): MapDirectionResponse

    /**
     * tmap 길찾기 (도보)
     */
    suspend fun requestTmapWalkRoad(
        tmapWalkRoadRequest: TmapWalkRoadRequest,
        appKey: String,
    ): FeatureCollectionResponse

    /**
     * 미확인 편지 리스트 수신
     */
    suspend fun getUncheckedLetter(): UncheckedLetterResponse

    /**
     * 편지 줍기
     */
    suspend fun getPickUpLetter(letter_id: Int): MessageResponse
}
