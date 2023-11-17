package com.zootopia.domain.usecase.preference

import android.util.Log
import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKakaoAccessTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    operator fun invoke(): Flow<String> {
        Log.d(TAG, "invoke: ${preferenceRepository.getKakaoAccessToken()}")
        return preferenceRepository.getKakaoAccessToken()
    }

    companion object{
        private const val TAG = "GetKakaoAccessTokenUseCase_HP"
    }
}