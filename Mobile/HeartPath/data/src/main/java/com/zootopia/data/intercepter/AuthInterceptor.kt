package com.zootopia.data.intercepter

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.model.auth.AuthResponse
import com.zootopia.data.model.error.ErrorResponse
import com.zootopia.domain.util.NetworkThrowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.closeQuietly
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
) : Interceptor {
    private val gson = Gson()
    private val client = OkHttpClient.Builder().build()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = getAccessToken() ?: return chain.proceed(chain.request())

        // 헤더에 토큰 담기
        val headerAddedRequest = chain.request().newBuilder().addHeader(AUTHORIZATION, accessToken).build()
        // 리스폰스 받기
        val response: Response = chain.proceed(headerAddedRequest)

        // 에러 코드가 왔을 떄, RefreshToken으로 AccessToken을 다시 얻어오는 로직
        if (response.code == AUTH_TOKEN_EXPIRE_ERROR) {
            val newAccessToken = getAccessTokenAfterRefresh(accessToken).getOrElse { return response }
            response.closeQuietly()
            return chain.proceed(chain.request().newBuilder().addHeader(AUTHORIZATION, newAccessToken).build())
        }
        return response
    }

    // AccessToken을 가져옵니다.
    private fun getAccessToken() : String? = runBlocking {
        return@runBlocking preferenceDataSource.getAccessToken().last()
    }

    // RefreshToken을 가져옵니다.
    private fun getRefreshToken(): String = runBlocking {
        return@runBlocking requireNotNull(preferenceDataSource.getRefreshToken().last()) { NO_REFRESH_TOKEN }
    }

    // Token을 저장합니다.
    private fun storeToken(accessToken: String, refreshToken: String) = runBlocking {
        preferenceDataSource.setAccessToken(accessToken)
        preferenceDataSource.setRefreshToken(refreshToken)
    }

    // 가지고 있는 RefreshToken으로 AccessToken을 재발급 합니다.
    // 서버 구현이 AccessToken이 필요 없다면 뺴도 됨.
    private fun getAccessTokenAfterRefresh(accessToken: String): Result<String> {
        val requestBody = createRefreshRequestBody()
        val request = createRefreshRequest(requestBody, accessToken)

        val auth: AuthResponse = requestRefresh(request).getOrElse {
            return Result.failure(it)
        }
        storeToken(auth.accessToken, auth.refreshToken)

        return Result.success(BEARER + auth.accessToken)
    }

    // 통신을 위해 JSON 형식으로 RequestCode를 생성합니다.
    private fun createRefreshRequestBody(): RequestBody {
        return JSONObject()
            .put(AUTH_REFRESH, getRefreshToken())
            .toString()
            .toRequestBody(contentType = "application/json".toMediaType())
    }

    // AccessToken을 다시 얻어올 Request를 생성합니다.
    private fun createRefreshRequest(requestBody: RequestBody, accessToken: String): Request {
        return Request.Builder()
            .url(BASE_URL + AUTH_REFRESH_PATH)
            .post(requestBody)
            .addHeader(AUTHORIZATION, accessToken)
            .build()
    }

    private inline fun <reified T> Response.getDto(): T {
        val responseObject = JsonParser.parseString(body?.string()).asJsonObject
        return gson.fromJson(responseObject, T::class.java)
    }

    // AccessToken을 재발급 합니다.
    private fun requestRefresh(request: Request): Result<AuthResponse> {
        val response: Response = runBlocking {
            withContext(Dispatchers.IO) { client.newCall(request).execute() }
        }
        if (response.isSuccessful) {
            return Result.success(response.getDto<AuthResponse>())
        }
        val failedResponse = response.getDto<ErrorResponse>() // TODO 서버에서 ErrorResponse 객체
        if (failedResponse.httpStatusCode == AUTH_TOKEN_ERROR) { // TODO 서버에서 주는 코드 입력
            return Result.failure(NetworkThrowable.Base400Throwable(failedResponse.httpStatusCode, failedResponse.message))
        }
        return Result.failure(IllegalStateException(REFRESH_FAILURE))
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val AUTH_REFRESH = "refreshToken"

        private const val AUTH_REFRESH_PATH = "/user/token"

        private const val BEARER = "Bearer "

        private const val NO_REFRESH_TOKEN = "리프레시 토큰이 없습니다"
        private const val REFRESH_FAILURE = "토큰 리프레시 실패"

        private const val AUTH_TOKEN_EXPIRE_ERROR = 1002 // TODO 서버에 맞게 수정
        private const val AUTH_TOKEN_ERROR = 1000 // TODO 서버에 맞게 수정

        private const val BASE_URL = "https://www.heartpath.site/"
    }
}