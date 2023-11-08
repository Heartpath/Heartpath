package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(accessToken: String, refreshToken: String) {

    }
}