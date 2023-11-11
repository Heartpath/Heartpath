package com.zootopia.data.repository.user

import com.zootopia.data.datasource.remote.user.UserDataSource
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.user.FriendDto
import com.zootopia.domain.model.user.PointDto
import com.zootopia.domain.model.user.SearchUserInfoDto
import com.zootopia.domain.model.user.UserInfoDto
import com.zootopia.domain.repository.user.UserRepository
import com.zootopia.domain.util.getValueOrThrow2

class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
): UserRepository {
    override suspend fun getUserInfo(): UserInfoDto? {
        return getValueOrThrow2 {
            userDataSource.getUserInfo().toDomain()
        }
    }

    override suspend fun getPointInfo(): List<PointDto>? {
        return getValueOrThrow2 {
            userDataSource.getPointInfo().toDomain()
        }
    }

    override suspend fun getFriendList(): List<FriendDto>? {
        return getValueOrThrow2 {
            userDataSource.getFriendList().toDomain()
        }
    }

    override suspend fun addFriend(id: String): String {
        return getValueOrThrow2 {
            userDataSource.addFriend(id = id).toDomain()
        }
    }

    override suspend fun searchUser(id: String, limit: Int): List<SearchUserInfoDto> {
        return getValueOrThrow2 {
            userDataSource.searchUser(id = id, limit = limit).toDomain()
        }
    }
}