package com.zootopia.domain.repository.user

import com.zootopia.domain.model.user.UserInfoDto

interface UserRepository {
    suspend fun getUserInfo(): UserInfoDto?
}