package com.zootopia.domain.usecase.store

import com.zootopia.domain.model.store.BuyStoreCharacterRequestDto
import com.zootopia.domain.repository.store.StoreRepository
import javax.inject.Inject

class BuyStoreCharacterUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(buyStoreCharacterRequestDto: BuyStoreCharacterRequestDto) {
        storeRepository.buyStoreCharacter(buyStoreCharacterRequestDto)
    }
}