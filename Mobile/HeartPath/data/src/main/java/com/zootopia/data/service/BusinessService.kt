package com.zootopia.data.service

import com.zootopia.data.model.business.request.PostHandLetterRequest
import com.zootopia.data.model.business.response.BusinessResponse
import okhttp3.MultipartBody
import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.request.SignupRequest
import com.zootopia.data.model.login.response.CheckIdResponse
import com.zootopia.data.model.login.response.LoginResponse
import com.zootopia.data.model.user.response.FriendListResponse
import com.zootopia.data.model.user.response.PointInfoResponse
import com.zootopia.data.model.user.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PUT
import retrofit2.http.Query

interface BusinessService {
    @GET("/user/health_check")
    suspend fun test(): Response<String>

    @Multipart
    @POST("/letter/hand")
    suspend fun postHandLetter(
        @Part("letterHandReqDto") postHandLetterRequest: PostHandLetterRequest,
        @Part content: MultipartBody.Part,
        @Part files: List<MultipartBody.Part>?
    ): Response<BusinessResponse>

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
    suspend fun getUserInfo(): Response<UserInfoResponse>

    // 사용자 정보 수정
    @PUT("/user/mypage")
    suspend fun editUserInfo()

    // 사용자 포인트 내역 조회
    @GET("/user/point")
    suspend fun getPointInfo(): Response<PointInfoResponse>

    // 사용자 포인트 적립
    @POST("/user/point")
    suspend fun earnedPoint()

    // 친구 목록 조회
    @GET("/user/friend")
    suspend fun getFriendList(): Response<FriendListResponse>

    // 친구 추가
    @POST("/user/friend")
    suspend fun addFriend(

    ): Response<MessageResponse>

}