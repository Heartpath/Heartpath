package com.zootopia.data.repository.store

import com.zootopia.data.datasource.remote.store.StoreDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.store.BuyStoreCharacterRequestDto
import com.zootopia.domain.model.store.BuyStoreLetterPaperRequestDto
import com.zootopia.domain.model.store.ChangeMainCharacterRequestDto
import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.model.store.StoreItemLetterPaperDto
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

    override suspend fun getStoreCharacterList(): MutableList<StoreCharacterDto> {
        return getValueOrThrow2 {
            storeDataSource.getStoreCharacterList().toDomain()
        }
    }

    override suspend fun getStoreItemLetterPaperList(): MutableList<StoreItemLetterPaperDto> {
        return getValueOrThrow2 {
            storeDataSource.getStoreItemLetterPaperList().toDomain()
        }
    }

    override suspend fun buyStoreCharacter(buyStoreCharacterRequestDto: BuyStoreCharacterRequestDto) {
        getValueOrThrow2 {
            storeDataSource.buyStoreCharacter(buyStoreCharacterRequestDto.toData())
        }
    }

    override suspend fun buyStoreLetterPaper(buyStoreLetterPaperRequestDto: BuyStoreLetterPaperRequestDto) {
        return getValueOrThrow2 {
            storeDataSource.buyStoreLetterPaper(buyStoreLetterPaperRequestDto.toData())
        }
    }

    override suspend fun changeMainCharacter(changeMainCharacterRequestDto: ChangeMainCharacterRequestDto) {
        return getValueOrThrow2 {
            storeDataSource.changeMainCharacter(changeMainCharacterRequestDto.toData())
        }
    }


}