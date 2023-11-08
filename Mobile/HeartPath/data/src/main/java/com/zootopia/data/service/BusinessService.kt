package com.zootopia.data.service

import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.request.SignupRequest
import com.zootopia.data.model.login.response.CheckIdResponse
import com.zootopia.data.model.login.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BusinessService {
    @GET("/user/health_check")
    suspend fun test(): Response<String>

    @POST("/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    // 아이디 중복 체크
    @GET("/user/check")
    suspend fun checkId(
        @Query("id") id: String
    ): Response<CheckIdResponse>

    // 회원가입
    @POST("/user/register")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ): Response<LoginResponse>

    // 회원 탈퇴
    @POST("/user/unregist")
    suspend fun unregister(): Response<MessageResponse>

    // 사용자 정보 조회
    @GET("/user/mypage")
    suspend fun getUserInfo()

    // 사용자 정보 수정
    @PUT("/user/mypage")
    suspend fun editUserInfo()

    // 사용자 포인트 내역 조회
    @GET("/user/point")
    suspend fun getPointInfo()

    // 사용자 포인트 적립
    @POST("/user/point")
    suspend fun earnedPoint()

}