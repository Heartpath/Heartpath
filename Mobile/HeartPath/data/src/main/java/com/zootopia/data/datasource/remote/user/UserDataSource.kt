package com.zootopia.data.datasource.remote.user

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.user.response.FriendListResponse
import com.zootopia.data.model.user.response.PointInfoResponse
import com.zootopia.data.model.user.response.SearchUserResponse
import com.zootopia.data.model.user.response.UserInfoResponse

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun getPointInfo(): PointInfoResponse
    suspend fun getFriendList(): FriendListResponse
    suspend fun addFriend(id: String): MessageResponse
    suspend fun searchUser(id: String, limit: Int, checkFriends: Boolean): SearchUserResponse
    
    /**
     * 친구 차단
     */
    suspend fun putOpponentFriend(opponentID: String): MessageResponse
}