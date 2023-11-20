package com.zootopia.domain.usecase.preference

import com.zootopia.domain.repository.PreferenceRepository
import javax.inject.Inject

class SetPermissionRejectedUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
) {
    suspend operator fun invoke(key: String, stack: Int) {
        return preferenceRepository.setPermissionRejected(key, stack)
    }
}
