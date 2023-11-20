package com.zootopia.data.repository

import android.util.Log
import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "PreferenceRepositoryImp_HeartPath"

class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource,
) : PreferenceRepository {

    override suspend fun getPermissionRejected(key: String): Flow<Int> {
        return preferenceDataSource.getPermissionRejected(key)
    }

    override suspend fun setPermissionRejected(key: String, stack: Int) {
        preferenceDataSource.setPermissionRejected(key, stack)
    }

    override fun getBgmState(key: String): Flow<Boolean> {
        return preferenceDataSource.getBgmState(key = key)
    }

    override suspend fun setBgmState(key: String, stateValue: Boolean) {
        preferenceDataSource.setBgmState(key = key, stateValue = stateValue)
    }

    override fun getFcmToken(): Flow<String> {
        return preferenceDataSource.getFcmToken()
    }

    override suspend fun setFcmToken(token: String) {
        Log.d(TAG, "setFcmToken: $token")
        preferenceDataSource.setFcmToken(token = token)
    }

    override fun getAccessToken(): Flow<String> {
        return preferenceDataSource.getAccessToken()
    }

    override suspend fun setToken(accessToken: String, refreshToken: String): Flow<Boolean> {
        return preferenceDataSource.setToken(accessToken = accessToken, refreshToken = refreshToken)
    }

    override fun getRefreshToken(): Flow<String> {
        return preferenceDataSource.getRefreshToken()
    }

    override fun getKakaoAccessToken(): Flow<String> {
        return preferenceDataSource.getKakaoAccessToken()
    }

    override suspend fun setKakaoAccessToken(accessToken: String) {
        Log.d(TAG, "setKakaoAccessToken: $accessToken")
        preferenceDataSource.setKakaoAccessToken(accessToken = accessToken)
    }

}