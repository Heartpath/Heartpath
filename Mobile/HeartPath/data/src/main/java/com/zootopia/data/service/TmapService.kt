package com.zootopia.data.service

import com.zootopia.data.model.map.request.TmapWalkRoadRequest
import com.zootopia.data.model.map.response.tmap.FeatureCollectionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TmapService {
    /**
     * Tmap 경로 서비스 요청
     */
    @POST("/tmap/routes/pedestrian?version=1&format=json&callback=result")
    suspend fun getPedestrianRoute(
        @Body requestData: TmapWalkRoadRequest,
        @Header("appKey") appKey: String,
    ): Response<FeatureCollectionResponse>
}