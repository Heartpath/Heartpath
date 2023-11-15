package com.zootopia.domain.usecase.store

import com.zootopia.domain.repository.store.StoreRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class PostPointUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(point: Int): String {
        return getValueOrThrow2 {
            storeRepository.postPoint(point = point)
        }
    }
}