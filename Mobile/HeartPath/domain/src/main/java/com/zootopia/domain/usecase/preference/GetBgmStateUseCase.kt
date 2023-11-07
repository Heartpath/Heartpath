package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetBgmStateUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
){
    operator fun invoke(key: String): Flow<Boolean> {
        return preferenceRepository.getBgmState(key = key)
    }
}