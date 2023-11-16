package com.zootopia.domain.usecase.preference

import android.util.Log
import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String): Flow<Boolean> {
        Log.d(TAG, "invoke: in use case")
        return repository.setToken(accessToken = accessToken, refreshToken = refreshToken)
    }
    companion object {
        private const val TAG = "SetTokenUseCase_HP"
    }
}