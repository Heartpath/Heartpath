package com.zootopia.domain.repository.login

import com.zootopia.domain.model.login.TokenDto
import retrofit2.Response

interface LoginRepository {
    suspend fun login(kakaoAccessToken: String, fcmToken: String): TokenDto?

    suspend fun checkId(newId: String): Boolean
    suspend fun signup(memberId: String,kakaoAccessToken: String, fcmToken: String): TokenDto?
}