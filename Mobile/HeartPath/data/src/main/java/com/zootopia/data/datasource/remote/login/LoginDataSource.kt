package com.zootopia.data.datasource.remote.login

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.request.SignupRequest
import com.zootopia.data.model.login.response.CheckIdResponse
import com.zootopia.data.model.login.response.LoginResponse

interface LoginDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun checkId(newId: String): CheckIdResponse
    suspend fun signup(signupRequest: SignupRequest): LoginResponse
}