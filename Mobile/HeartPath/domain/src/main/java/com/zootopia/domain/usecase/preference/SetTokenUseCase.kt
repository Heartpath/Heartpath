package com.zootopia.domain.usecase.preference

import android.util.Log
import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) {
        Log.d(TAG, "invoke: in use case")
        repository.setToken(accessToken = accessToken, refreshToken = refreshToken)
    }
    companion object {
        private const val TAG = "SetTokenUseCase_HP"
    }
}