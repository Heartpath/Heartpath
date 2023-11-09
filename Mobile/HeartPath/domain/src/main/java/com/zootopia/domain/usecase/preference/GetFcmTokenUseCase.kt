package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke() : Flow<String> {
        return preferenceRepository.getFcmToken()
    }
}