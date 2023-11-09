package com.zootopia.data.datasource.remote.user

import com.zootopia.data.model.user.response.UserInfoResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class UserDataSourceImpl(
    private val businessService: BusinessService
): UserDataSource {
    override suspend fun getUserInfo(): UserInfoResponse {
        return handleApi { businessService.getUserInfo() }
    }
}