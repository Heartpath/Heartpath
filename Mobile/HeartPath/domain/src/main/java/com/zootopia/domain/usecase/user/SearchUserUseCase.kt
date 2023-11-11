package com.zootopia.domain.usecase.user

import com.zootopia.domain.model.user.SearchUserInfoDto
import com.zootopia.domain.repository.user.UserRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String, limit: Int): List<SearchUserInfoDto> {
        return userRepository.searchUser(id, limit)
    }
}