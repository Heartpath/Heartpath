package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
){
    suspend operator fun invoke(): Flow<String> {
        return preferenceRepository.getAccessToken()
    }
}