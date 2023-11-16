package com.zootopia.data.intercepter

import android.util.Log
import com.google.gson.Gson
import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.model.error.ErrorResponse
import com.zootopia.data.service.BusinessService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class ResponseInterceptor@Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
): Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        var accessToken = ""
        var isRefreshable = false

        Log.d(TAG, "intercept: 지금 코드 ${response.code}")
        Log.d(TAG, "intercept: 지금 네트워크 리스폰스 ${response.networkResponse}")

        when (response.code) {
            400 -> {
                Log.d(TAG, "intercept: 에러 : 400 에러입니다.")
            }

            AUTH_TOKEN_EXPIRE_ERROR -> { // 여러 에러들 종합 (에러 메시지로 확인하자.)
                val errorResponse = parseErrorResponse(response.body)
                Log.d(TAG, "intercept: 에러 바디 파싱 !!!!!!!!!! ${errorResponse}")

                // 에러 코드로 분기
                when (errorResponse.httpStatus) {
                    "401 UNAUTHORIZED" -> { // 엑세스 토큰 만료 신호
                        Log.d(TAG, "intercept: 401 UNAUTHORIZED 만료된 토큰")
                        runBlocking {
                            //토큰 갱신 api 호출
                            getRefreshToken().let {refreshTokenValue ->
                                Log.d(TAG, "intercept: $refreshTokenValue")

                                val result = Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(BusinessService::class.java).getReAccessToken(refreshTokenValue)

                                Log.d(TAG, "intercept: $result")
                                if (result.isSuccessful) {
                                    Log.d(TAG, "intercept: 다시 받기 성공")
                                    storeToken(accessToken = result.body()!!.data)
                                    Log.d(TAG, "intercept: 재발급한 access token ${result.body()!!.data}")
                                    accessToken = result.body()!!.data
                                    isRefreshable = true
                                }
                                if (result.body() == null) {
                                    Log.d(TAG, "intercept: refresh token로 재발급 실패")
                                    throw (IOException(REFRESH_FAILURE))
                                }
                            }
                        }
                    }
                    "Auth-004" -> { // 엑세스 토큰 invalid 신호
                        Log.d(TAG, "intercept: 에러(Auth-004) : 해당 토큰은 엑세스 토큰이 아닙니다.")
                    }
                    "Auth-007" -> {
                        throw (IOException(REFRESH_FAILURE))
                    }
                }
            }

            403 -> {
                Log.d(TAG, "intercept: 에러 : 403 에러입니다.")
                val errorResponse = parseErrorResponse(response.body)
                Log.d(TAG, "intercept: 에러 바디 ${errorResponse}")

                // 에러 코드로 분기
                when (errorResponse.errorCode) {
                    "Auth-009" -> {
                        Log.d(TAG, "intercept: 다시 로그인 해야 합니다.")
                        throw (IOException("required_re_login"))
                    }
                }
            }

            AUTH_TOKEN_ERROR -> {
                Log.d(TAG, "intercept: 에러 : 잘못된 값 에러입니다.")
            }

            500 -> { // 서버에러
                Log.d(TAG, "intercept: 에러 : 500 에러입니다.")
            }
        }

        // 다시 내가 호출했었던 거 호출하는 로직 필요할듯?
        if(isRefreshable) {
            Log.d(TAG, "intercept: refresh 성공, new access token으로 재호출")
            val newRequest = chain.request().newBuilder().addHeader(AUTHORIZATION, BEARER  + accessToken).build()
            return chain.proceed(newRequest)
        }
        return response
    }

    private fun parseErrorResponse(responseBody: ResponseBody?): ErrorResponse {
        val gson = Gson()
        return gson.fromJson(responseBody?.charStream(), ErrorResponse::class.java)
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

    // RefreshToken을 가져옵니다.
    private fun getRefreshToken(): String {
        var result: String = ""
        runBlocking {
            result = preferenceDataSource.getRefreshToken().first()
            Log.d(TAG, "getRefreshToken: here $result")
        }
        return result
    }

    private fun storeToken(accessToken: String) = runBlocking {
        Log.d(TAG, "storeToken: $accessToken")
        preferenceDataSource.setAccessToken(accessToken)
    }

    companion object {
        private const val TAG = "ResponseInterceptor_HP"
        private const val AUTHORIZATION = "Authorization"
        private const val AUTH_REFRESH = "refreshToken"

        private const val BEARER = "Bearer "

        private const val NO_REFRESH_TOKEN = "리프레시 토큰이 없습니다"
        private const val REFRESH_FAILURE = "토큰 리프레시 실패"

        private const val AUTH_TOKEN_EXPIRE_ERROR = 401 // TODO 서버에 맞게 수정
        private const val AUTH_TOKEN_ERROR = 406 // TODO 서버에 맞게 수정

        private const val BASE_URL = "https://www.heartpath.site"
    }
}