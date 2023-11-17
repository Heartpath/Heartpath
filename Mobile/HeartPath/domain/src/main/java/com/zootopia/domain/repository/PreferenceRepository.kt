package com.zootopia.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getPermissionRejected(key: String) : Flow<Int>
    suspend fun setPermissionRejected(key : String, stack: Int)
    fun getBgmState(key: String) : Flow<Boolean>
    suspend fun setBgmState(key : String, stateValue: Boolean)

    fun getFcmToken() : Flow<String>
    suspend fun setFcmToken(token: String)

    fun getAccessToken() : Flow<String>
    suspend fun setToken(accessToken: String, refreshToken: String): Flow<Boolean>
    fun getRefreshToken() : Flow<String>
    fun getKakaoAccessToken(): Flow<String>
    suspend fun setKakaoAccessToken(accessToken: String)
}