package com.zootopia.data.service

import com.zootopia.data.model.map.response.MapDirectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverService {
    /**
     * 네이버 맵 길찾기 요청
     */
    @GET("/map-direction/v1/driving")
    suspend fun getDrivingDirections(
        @Query("start") startLocation: String,
        @Query("goal") goalLocation: String,
        @Query("option") option: String,
        @Header("X-NCP-APIGW-API-KEY-ID") apiKeyId: String,
        @Header("X-NCP-APIGW-API-KEY") apiKey: String
    ): Response<MapDirectionResponse> // Replace with your response model class
}