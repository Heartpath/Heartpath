package com.zootopia.data.datasource.remote.store

import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse

interface StoreDataSource {
    suspend fun getCharacterEncyclopediaList(): CharacterEncyclopediaListResponse
    suspend fun getStoreCharacterList(): StoreCharacterListResponse
}