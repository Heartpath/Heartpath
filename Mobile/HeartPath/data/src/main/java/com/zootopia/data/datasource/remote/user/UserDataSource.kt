package com.zootopia.data.datasource.remote.user

import com.zootopia.data.model.user.response.UserInfoResponse

interface UserDataSource {
    suspend fun getUserInfo(): UserInfoResponse
}