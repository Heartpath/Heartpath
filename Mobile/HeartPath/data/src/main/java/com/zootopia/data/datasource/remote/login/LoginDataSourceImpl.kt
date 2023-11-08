package com.zootopia.data.datasource.remote.login

import android.util.Log
import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.request.SignupRequest
import com.zootopia.data.model.login.response.CheckIdResponse
import com.zootopia.data.model.login.response.LoginResponse
import com.zootopia.data.service.BusinessService
import com.zootopia.data.util.handleApi

class LoginDataSourceImpl(
    private val businessService: BusinessService
): LoginDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        Log.d(TAG, "login: 여기까지 도달")
        return handleApi { businessService.login(loginRequest = loginRequest) }
    }

    override suspend fun checkId(newId: String): CheckIdResponse {
        return handleApi { businessService.checkId(id = newId) }
    }

    override suspend fun signup(signupRequest: SignupRequest): LoginResponse {
        return handleApi { businessService.signup(signupRequest = signupRequest) }
    }

    companion object {
        private const val TAG = "LoginDataSourceImpl_HP"
    }
}