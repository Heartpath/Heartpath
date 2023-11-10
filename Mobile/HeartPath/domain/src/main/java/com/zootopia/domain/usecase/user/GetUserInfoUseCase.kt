package com.zootopia.domain.usecase.user

import com.zootopia.domain.model.user.UserInfoDto
import com.zootopia.domain.repository.user.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserInfoDto? {
        return userRepository.getUserInfo()
    }
}