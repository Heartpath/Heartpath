package com.zootopia.data.intercepter

import android.util.Log
import com.zootopia.data.datasource.local.PreferenceDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = getAccessToken() ?: return chain.proceed(chain.request())
        // 헤더에 토큰 담기
        val headerAddedRequest =
            chain.request().newBuilder()
                .addHeader(AUTHORIZATION, BEARER + accessToken)
                .build()
        // 리스폰스 받기
        val response: Response = chain.proceed(headerAddedRequest)
        return response
    }

    // AccessToken을 가져옵니다.
    private fun getAccessToken(): String? {
        var result: String? = null
        runBlocking {
            result = preferenceDataSource.getAccessToken().first()
            Log.d(TAG, "getAccessToken: here $result")
        }
        return result
    }
    companion object {
        private const val AUTHORIZATION = "Authorization"

        private const val BEARER = "Bearer "

        private const val TAG = "AuthInterceptor_HP"
    }
}