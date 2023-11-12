package com.zootopia.data.datasource.remote.store

import com.zootopia.data.model.store.CharacterEncyclopediaListResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class StoreDataSourceImpl(
    private val businessService: BusinessService
): StoreDataSource {
    override suspend fun getCharacterEncyclopediaList(): CharacterEncyclopediaListResponse {
        return handleApi {
            businessService.getCharacterEncyclopediaList()
        }
    }

}