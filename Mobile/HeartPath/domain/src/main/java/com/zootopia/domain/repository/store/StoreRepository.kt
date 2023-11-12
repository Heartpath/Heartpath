package com.zootopia.domain.repository.store

import com.zootopia.domain.model.store.CharacterDto

interface StoreRepository {
    suspend fun getCharacterEncyclopedia(): MutableList<CharacterDto>
}