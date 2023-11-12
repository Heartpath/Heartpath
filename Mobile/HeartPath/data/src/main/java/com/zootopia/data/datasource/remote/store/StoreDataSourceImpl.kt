package com.zootopia.data.datasource.remote.store

import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse
import com.zootopia.data.model.store.response.StoreItemLetterPaperListResponse
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

    override suspend fun getStoreCharacterList(): StoreCharacterListResponse {
        return handleApi {
            businessService.getStoreCharacterList()
        }
    }

    override suspend fun getStoreItemLetterPaperList(): StoreItemLetterPaperListResponse {
        return handleApi {
            businessService.getStoreItemLetterPaperList()
        }
    }


}