package com.zootopia.domain.usecase.user

import com.zootopia.domain.repository.user.UserRepository
import javax.inject.Inject

class AddFriendUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(id: String): String {
        return userRepository.addFriend(id = id)
    }
}