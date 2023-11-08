package com.zootopia.data.datasource.remote.login

import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.response.LoginResponse

interface LoginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse

}