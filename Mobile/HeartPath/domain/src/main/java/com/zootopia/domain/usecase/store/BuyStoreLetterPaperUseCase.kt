package com.zootopia.domain.usecase.store

import com.zootopia.domain.model.store.BuyStoreCharacterRequestDto
import com.zootopia.domain.model.store.BuyStoreLetterPaperRequestDto
import com.zootopia.domain.repository.store.StoreRepository
import javax.inject.Inject

class BuyStoreLetterPaperUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(buyStoreLetterPaperRequestDto: BuyStoreLetterPaperRequestDto) {
        storeRepository.buyStoreLetterPaper(buyStoreLetterPaperRequestDto)
    }
}