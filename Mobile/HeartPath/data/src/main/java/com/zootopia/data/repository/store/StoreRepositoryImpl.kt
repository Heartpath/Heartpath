package com.zootopia.data.repository.store

import com.zootopia.data.datasource.remote.store.StoreDataSource
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.repository.store.StoreRepository
import com.zootopia.domain.util.getValueOrThrow2

class StoreRepositoryImpl(
    private val storeDataSource: StoreDataSource
): StoreRepository {
    override suspend fun getCharacterEncyclopedia(): MutableList<CharacterDto> {
        return getValueOrThrow2 {
            storeDataSource.getCharacterEncyclopediaList().toDomain()
        }
    }

}