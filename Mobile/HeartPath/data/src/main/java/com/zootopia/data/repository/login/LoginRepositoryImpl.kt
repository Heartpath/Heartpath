package com.zootopia.data.repository.login

import android.util.Log
import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.datasource.remote.login.LoginDataSource
import com.zootopia.data.mapper.toData
import com.zootopia.data.mapper.toDomain
import com.zootopia.domain.model.login.LoginDto
import com.zootopia.domain.model.login.TokenDto
import com.zootopia.domain.repository.login.LoginRepository
import com.zootopia.domain.util.getValueOrThrow2
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : LoginRepository {
    override suspend fun login(kakaoAccessToken: String, fcmToken: String): TokenDto {
        return getValueOrThrow2{
            loginDataSource.login(
                LoginDto(
                    kakaoAccessToken = kakaoAccessToken,
                    fcmToken = fcmToken
                ).toData()
            ).toDomain()
        }
    }
    companion object {
        private const val TAG = "LoginRepositoryImpl_HP"
    }
}