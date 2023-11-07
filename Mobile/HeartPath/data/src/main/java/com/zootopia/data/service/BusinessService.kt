package com.zootopia.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface BusinessService {
    @GET("/user/health_check")
    suspend fun test(): Response<String>

    @POST("/user/login")
    suspend fun login(kakaoAccessToken: String)

    // 아이디 중복 체크
    @GET("/user/check/{id}")
    suspend fun checkId()

    // 회원 탈퇴
    @POST("/user/unregist")
    suspend fun unregister()

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