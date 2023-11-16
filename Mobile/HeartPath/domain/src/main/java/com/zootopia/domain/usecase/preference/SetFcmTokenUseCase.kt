package com.zootopia.domain.usecase.preference

import android.util.Log
import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetFcmTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
){
    suspend operator fun invoke(token: String) {
        preferenceRepository.setFcmToken(token = token)
    }

    companion object{
        private const val TAG = "SetFcmTokenUseCase_HP"
    }
}