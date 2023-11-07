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
    suspend fun setAccessToken(accessToken: String)

    fun getRefreshToken() : Flow<String>
    suspend fun setRefreshToken(refreshToken: String)
    fun getKakaoAccessToken(): Flow<String>
    suspend fun setKakaoAccessToken(accessToken: String)
}