package com.zootopia.data.datasource.remote.store

import com.zootopia.data.model.store.CharacterEncyclopediaListResponse

interface StoreDataSource {
    suspend fun getCharacterEncyclopediaList(): CharacterEncyclopediaListResponse
}