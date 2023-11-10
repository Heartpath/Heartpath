package com.zootopia.domain.usecase.user

import com.zootopia.domain.model.user.PointDto
import com.zootopia.domain.repository.user.UserRepository
import javax.inject.Inject

class GetPointInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<PointDto>? {
        return userRepository.getPointInfo()
    }
}