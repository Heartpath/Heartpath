package com.zootopia.domain.repository.store

import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.model.store.StoreCharacterDto

interface StoreRepository {
    suspend fun getCharacterEncyclopedia(): MutableList<CharacterDto>
    suspend fun getStoreCharacterList(): MutableList<StoreCharacterDto>
}