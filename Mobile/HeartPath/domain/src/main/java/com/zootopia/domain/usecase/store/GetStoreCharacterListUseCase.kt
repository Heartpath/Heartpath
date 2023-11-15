package com.zootopia.domain.usecase.store

import com.zootopia.domain.model.store.StoreCharacterDto
import com.zootopia.domain.repository.store.StoreRepository
import javax.inject.Inject

class GetStoreCharacterListUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(): MutableList<StoreCharacterDto> {
        return storeRepository.getStoreCharacterList()
    }
}