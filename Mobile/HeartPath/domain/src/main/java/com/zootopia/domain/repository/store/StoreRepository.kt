package com.zootopia.domain.repository.store

import com.zootopia.domain.model.store.BuyStoreCharacterRequestDto
import com.zootopia.domain.model.store.BuyStoreLetterPaperRequestDto
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreItemLetterPaperDto

interface StoreRepository {
    suspend fun getCharacterEncyclopedia(): MutableList<CharacterDto>
    suspend fun getStoreCharacterList(): MutableList<StoreCharacterDto>
    suspend fun getStoreItemLetterPaperList(): MutableList<StoreItemLetterPaperDto>
    suspend fun buyStoreCharacter(buyStoreCharacterRequestDto: BuyStoreCharacterRequestDto)
    suspend fun buyStoreLetterPaper(buyStoreLetterPaperRequestDto: BuyStoreLetterPaperRequestDto)
    
    /**
     * 포인트 적립
     */
    suspend fun postPoint(point: Int): String
}