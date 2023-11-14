package com.zootopia.domain.usecase.store

import com.zootopia.domain.model.store.ChangeMainCharacterRequestDto
import com.zootopia.domain.repository.store.StoreRepository
import javax.inject.Inject

class ChangeMainCharacterUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(changeMainCharacterRequestDto: ChangeMainCharacterRequestDto) {
        return storeRepository.changeMainCharacter(changeMainCharacterRequestDto)
    }
}