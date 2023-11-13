package com.zootopia.data.datasource.remote.store

import com.zootopia.data.model.store.request.BuyStoreCharacterRequest
import com.zootopia.data.model.store.request.BuyStoreLetterPaperRequest
import com.zootopia.data.model.store.request.ChangeMainCharacterRequest
import com.zootopia.data.model.store.response.BuyStoreCharacterResponse
import com.zootopia.data.model.store.response.BuyStoreLetterPaperResponse
import com.zootopia.data.model.store.response.ChangeMainCharacterResponse
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

    override suspend fun buyStoreCharacter(buyStoreCharacterRequest: BuyStoreCharacterRequest): BuyStoreCharacterResponse {
        return handleApi {
            businessService.buyStoreCharacter(buyStoreCharacterRequest)
        }
    }

    override suspend fun buyStoreLetterPaper(buyStoreLetterPaperRequest: BuyStoreLetterPaperRequest): BuyStoreLetterPaperResponse {
        return handleApi {
            businessService.buyStoreLetterPaper(buyStoreLetterPaperRequest)
        }
    }

    override suspend fun changeMainCharacter(changeMainCharacterRequest: ChangeMainCharacterRequest): ChangeMainCharacterResponse {
        return handleApi {
            businessService.changeMainCharacter(changeMainCharacterRequest)
        }
    }

}