package com.zootopia.domain.usecase.user

import com.zootopia.domain.repository.user.UserRepository
import com.zootopia.domain.util.getValueOrThrow2
import javax.inject.Inject

class PutOpponentFriendUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(opponentID: String): String {
        return getValueOrThrow2 {
            userRepository.putOpponentFriend(opponentID = opponentID)
        }
    }
}