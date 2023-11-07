package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetFcmTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
){
    suspend operator fun invoke(token: String) {
        preferenceRepository.setFcmToken(token = token)
    }
}