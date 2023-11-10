package com.zootopia.domain.repository.user

import com.zootopia.domain.model.user.FriendDto
import com.zootopia.domain.model.user.PointDto
import com.zootopia.domain.model.user.UserInfoDto

interface UserRepository {
    suspend fun getUserInfo(): UserInfoDto?
    suspend fun getPointInfo(): List<PointDto>?
    suspend fun getFriendList(): List<FriendDto>?
}