package com.zootopia.data.datasource.remote.user

import com.zootopia.data.model.user.response.FriendListResponse
import com.zootopia.data.model.user.response.PointInfoResponse
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
}