package com.zootopia.domain.usecase.store

import com.zootopia.domain.model.store.StoreItemLetterPaperDto
import com.zootopia.domain.repository.store.StoreRepository
import javax.inject.Inject

class GetStoreItemLetterPaperListUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(): MutableList<StoreItemLetterPaperDto> {
        return storeRepository.getStoreItemLetterPaperList()
    }
}
