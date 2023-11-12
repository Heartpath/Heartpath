package com.zootopia.data.service

import com.zootopia.data.model.auth.AuthResponse
import com.zootopia.data.model.letter.request.PostHandLetterRequest
import com.zootopia.data.model.letter.response.BusinessResponse
import okhttp3.MultipartBody
import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.response.GetUserLetterPaperResponse
import com.zootopia.data.model.letter.response.ReceivedLetterDetailResponse
import com.zootopia.data.model.letter.response.StoredLetterListResponse
import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
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
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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
    @GET("/store/point")
    suspend fun getPointInfo(): Response<PointInfoResponse>

    // 사용자 포인트 적립
    @POST("/store/point")
    suspend fun earnedPoint()

    // 친구 목록 조회
    @GET("/user/friend")
    suspend fun getFriendList(): Response<FriendListResponse>

    // 친구 추가
    @POST("/user/friend/{opponentId}")
    suspend fun addFriend(
        @Path("opponentId") id: String
    ): Response<MessageResponse>

    // 미발송 편지 목록 조회
    @GET("/letter/unplaced")
    suspend fun getUnplacedLetter(): Response<UnplacedLetterListResponse>
    // 열람한 수신 편지 목록 조회
    @GET("/letter/checked")
    suspend fun getStoredLetterList(): Response<StoredLetterListResponse>

    // 유저가 보유한 편지지 조회
    @GET("/store/letterpaper")
    suspend fun getUserLetterPaper(): Response<GetUserLetterPaperResponse>

    // 토큰 재발급
    @GET("/user/token")
    suspend fun getReAccessToken(@Query("refreshToken") refreshToken: String): Response<AuthResponse>

    // 편지 상세 보기
    @GET("letter/{letter_id}")
    suspend fun getLetter(@Path("letter_id") letterId: Int): Response<ReceivedLetterDetailResponse>
}