package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetKakaoAccessToken @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(accessToken: String) {
        preferenceRepository.setKakaoAccessToken(accessToken = accessToken)
    }
}