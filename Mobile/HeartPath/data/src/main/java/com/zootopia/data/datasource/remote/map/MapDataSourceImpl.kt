package com.zootopia.data.datasource.remote.map

import com.zootopia.data.model.map.response.MapDirectionResponse
import com.zootopia.data.service.NaverService
import com.zootopia.data.util.handleApi

class MapDataSourceImpl (
    private val naverService: NaverService
) : MapDataSource {
    override suspend fun getNaverMapDirection(
        start: String,
        goal: String,
        option: String,
        apiKeyId: String,
        apiKey: String
    ): MapDirectionResponse {
        return handleApi {
            naverService.getDrivingDirections(
                start,
                goal,
                option,
                apiKeyId,
                apiKey
            )
        }
    }
    
}