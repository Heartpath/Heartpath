package com.zootopia.data.datasource.remote.store

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.store.request.BuyStoreCharacterRequest
import com.zootopia.data.model.store.request.BuyStoreLetterPaperRequest
import com.zootopia.data.model.store.request.ChangeMainCharacterRequest
import com.zootopia.data.model.store.response.BuyStoreCharacterResponse
import com.zootopia.data.model.store.response.BuyStoreLetterPaperResponse
import com.zootopia.data.model.store.response.ChangeMainCharacterResponse
import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.GetMainCharacterResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse
import com.zootopia.data.model.store.response.StoreItemLetterPaperListResponse

interface StoreDataSource {
    suspend fun getCharacterEncyclopediaList(): CharacterEncyclopediaListResponse
    suspend fun getStoreCharacterList(): StoreCharacterListResponse
    suspend fun getStoreItemLetterPaperList(): StoreItemLetterPaperListResponse
    suspend fun buyStoreCharacter(buyStoreCharacterRequest: BuyStoreCharacterRequest): BuyStoreCharacterResponse
    suspend fun buyStoreLetterPaper(buyStoreLetterPaperRequest: BuyStoreLetterPaperRequest): BuyStoreLetterPaperResponse
    
    /**
     * 포인트 적립
     */
    suspend fun postPoint(point: Int): MessageResponse
    suspend fun changeMainCharacter(changeMainCharacterRequest: ChangeMainCharacterRequest): ChangeMainCharacterResponse
    suspend fun getMainCharacter(): GetMainCharacterResponse

}