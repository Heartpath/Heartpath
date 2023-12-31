package com.zootopia.data.datasource.remote.user

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.user.response.FriendListResponse
import com.zootopia.data.model.user.response.PointInfoResponse
import com.zootopia.data.model.user.response.SearchUserResponse
import com.zootopia.data.model.user.response.UserInfoResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class UserDataSourceImpl(
    private val businessService: BusinessService
): UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponse {
        return handleApi { businessService.getUserInfo() }
    }

    override suspend fun getPointInfo(): PointInfoResponse {
        return handleApi { businessService.getPointInfo() }
    }

    override suspend fun getFriendList(): FriendListResponse {
        return handleApi { businessService.getFriendList() }
    }

    override suspend fun addFriend(id: String): MessageResponse {
        return handleApi { businessService.addFriend(id = id) }
    }

    override suspend fun searchUser(id: String, limit: Int, checkFriends: Boolean): SearchUserResponse {
        return handleApi { businessService.searchUser(id = id, limit = limit, checkFriends = checkFriends) }
    }
    
    /**
     * 친구 차단
     */
    override suspend fun putOpponentFriend(opponentID: String): MessageResponse {
        return handleApi { businessService.putOpponentFriend(opponentID = opponentID) }
    }
    
}