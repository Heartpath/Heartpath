package com.zootopia.domain.usecase.preference

import android.util.Log
import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetKakaoAccessTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(accessToken: String) {
        Log.d(TAG, "usecase kakao token: $accessToken")
        preferenceRepository.setKakaoAccessToken(accessToken = accessToken)
    }

    companion object {
        private const val TAG = "SetKakaoAccessTokenUseCase_HP"

    }
}