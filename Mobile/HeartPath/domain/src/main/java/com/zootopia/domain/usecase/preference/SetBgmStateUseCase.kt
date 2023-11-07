package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetBgmStateUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
){
    suspend operator fun invoke(key: String, stateValue: Boolean) {
        preferenceRepository.setBgmState(key = key, stateValue = stateValue)
    }
}