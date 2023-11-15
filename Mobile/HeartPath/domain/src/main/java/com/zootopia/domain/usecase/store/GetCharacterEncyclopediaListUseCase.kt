package com.zootopia.domain.usecase.store

import com.zootopia.domain.model.store.CharacterDto
import com.zootopia.domain.repository.store.StoreRepository
import javax.inject.Inject

class GetCharacterEncyclopediaListUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(): MutableList<CharacterDto> {
        return storeRepository.getCharacterEncyclopedia()
    }
}