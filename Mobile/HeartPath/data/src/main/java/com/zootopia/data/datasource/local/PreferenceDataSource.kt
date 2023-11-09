package com.zootopia.data.datasource.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

// datastore 객체를 파일의 최상단에 올려주어 싱글톤으로 사용
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "HeartPath.pd"
)

class PreferenceDataSource @Inject constructor(
    @ApplicationContext private val context: Context, // Hilt를 사용하면서 앱 Context를 참고해야할 경우 사용해야함...
) {
    private object PreferenceKeys {
        val PERMISSION_REJECTED = intPreferencesKey("permission_rejected")
        val BGM_STATE = booleanPreferencesKey("bgm_state")  // BGM 상태
        val FCM_TOKEN = stringPreferencesKey("fcm_token")   // FCM 토큰값
        val ACCESS_TOKEN = stringPreferencesKey("access_token") // access token
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")   // refresh_token
        val KAKAO_ACCESS_TOKEN = stringPreferencesKey("kakao_access_token")     // 카카오 access token
    }

    fun getPermissionRejected(key: String): Flow<Int> {
        val rejectedPreferencesFlow: Flow<Int> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[intPreferencesKey(key)] ?: 0
            }

        return rejectedPreferencesFlow
    }

    suspend fun setPermissionRejected(key: String, stack: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = stack + 1
        }
    }

    // BGM 상태 확인
    fun getBgmState(key: String): Flow<Boolean> {
        val bgmStateFlow: Flow<Boolean> = context.dataStore.data
            .catch { exception ->
                // IOException이 발생하는 경우도 있기 때문에 throw-catch 처리
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[booleanPreferencesKey(key)] ?: true
            }
        return bgmStateFlow
    }

    // BGM 상태 수정
    suspend fun setBgmState(key: String, stateValue: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = stateValue
        }
    }

    // FCM token 값 호출
    fun getFcmToken(): Flow<String> {
        val fcmTokenFlow: Flow<String> = context.dataStore.data
            .catch { exception ->
                // IOException이 발생하는 경우도 있기 때문에 throw-catch 처리
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[stringPreferencesKey("fcm_token")] ?: ""
            }
        return fcmTokenFlow
    }

    // FCM token 값 수정
    suspend fun setFcmToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("fcm_token")] = token
        }
    }

    // access token 값 호출
    fun getAccessToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                // IOException이 발생하는 경우도 있기 때문에 throw-catch 처리
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[stringPreferencesKey("access_token")] ?: ""
            }
    }

    // access token 값 수정
    suspend fun setAccessToken(accessToken: String) {
        Log.d(TAG, "setAccessToken: $accessToken")
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("access_token")] = accessToken
        }
    }

    // refresh token 값 호출
    fun getRefreshToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                // IOException이 발생하는 경우도 있기 때문에 throw-catch 처리
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[stringPreferencesKey("refresh_token")] ?: ""
            }
    }

    // refresh token 값 수정
    suspend fun setRefreshToken(refreshToken: String) {
        Log.d(TAG, "setRefreshToken: $refreshToken")
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("refresh_token")] = refreshToken
        }
    }

    // kakao access token 값 호출
    fun getKakaoAccessToken(): Flow<String> {
        return context.dataStore.data
            .catch { exception ->
                // IOException이 발생하는 경우도 있기 때문에 throw-catch 처리
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[stringPreferencesKey("kakao_access_token")] ?: ""
            }
    }
    // kakao access token 값 수정
    suspend fun setKakaoAccessToken(accessToken: String) {
        context.dataStore.edit {preferences ->
            preferences[stringPreferencesKey("kakao_access_token")] = accessToken
        }
    }
    companion object {
        private const val TAG = "PreferenceDataSource_HP"
    }
}