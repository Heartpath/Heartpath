package com.zootopia.data.datasource.remote.map

import com.zootopia.data.model.map.response.MapDirectionResponse

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
}