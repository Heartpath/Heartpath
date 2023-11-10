package com.zootopia.domain.usecase.user

import com.zootopia.domain.model.user.FriendDto
import com.zootopia.domain.repository.user.UserRepository
import javax.inject.Inject

class GetFriendListUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): List<FriendDto>? {
        return userRepository.getFriendList()
    }
}