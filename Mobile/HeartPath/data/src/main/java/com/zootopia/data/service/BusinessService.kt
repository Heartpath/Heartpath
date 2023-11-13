package com.zootopia.data.service

import com.zootopia.data.model.auth.AuthResponse
import com.zootopia.data.model.common.MessageResponse
import com.zootopia.data.model.letter.request.LetterPlacedRequest
import com.zootopia.data.model.letter.request.PostHandLetterRequest
import com.zootopia.data.model.letter.request.PostTypingLetterRequest
import com.zootopia.data.model.letter.response.BusinessResponse
import com.zootopia.data.model.letter.response.GetUserLetterPaperResponse
import com.zootopia.data.model.letter.response.ReceivedLetterDetailResponse
import com.zootopia.data.model.letter.response.StoredLetterListResponse
import com.zootopia.data.model.letter.response.UncheckedLetterResponse
import com.zootopia.data.model.letter.response.UnplacedLetterListResponse
import com.zootopia.data.model.login.request.LoginRequest
import com.zootopia.data.model.login.request.SignupRequest
import com.zootopia.data.model.login.response.CheckIdResponse
import com.zootopia.data.model.login.response.LoginResponse
import com.zootopia.data.model.store.request.BuyStoreCharacterRequest
import com.zootopia.data.model.store.request.BuyStoreLetterPaperRequest
import com.zootopia.data.model.store.response.BuyStoreCharacterResponse
import com.zootopia.data.model.store.response.BuyStoreLetterPaperResponse
import com.zootopia.data.model.store.response.CharacterEncyclopediaListResponse
import com.zootopia.data.model.store.response.StoreCharacterListResponse
import com.zootopia.data.model.store.response.StoreItemLetterPaperListResponse
import com.zootopia.data.model.user.response.FriendListResponse
import com.zootopia.data.model.user.response.PointInfoResponse
import com.zootopia.data.model.user.response.SearchUserResponse
import com.zootopia.data.model.user.response.UserInfoResponse
import okhttp3.MultipartBody
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
        @Part files: List<MultipartBody.Part>?,
    ): Response<BusinessResponse>

    @POST("/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>

    // 아이디 중복 체크
    @GET("/user/check")
    suspend fun checkId(
        @Query("id") id: String,
    ): Response<CheckIdResponse>

    // 회원가입
    @POST("/user/register")
    suspend fun signup(
        @Body signupRequest: SignupRequest,
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
        @Path("opponentId") id: String,
    ): Response<MessageResponse>

    // 미발송 편지 목록 조회
    @GET("/letter/unplaced")
    suspend fun getUnplacedLetter(): Response<UnplacedLetterListResponse>

    // 편지 배치
    @Multipart
    @POST("letter/placed")
    suspend fun requestLetterPlaced(
        @Part files: MultipartBody.Part?,
        @Part("letterPlaceReqDto") letterPlacedRequest: LetterPlacedRequest,
    ): Response<MessageResponse>

    // 열람한 수신 편지 목록 조회
    @GET("/letter/checked")
    suspend fun getStoredLetterList(): Response<StoredLetterListResponse>

    // 유저가 보유한 편지지 조회
    @GET("/store/letterpaper")
    suspend fun getUserLetterPaper(): Response<GetUserLetterPaperResponse>

    // 타이핑 편지 작성
    @Multipart
    @POST("/letter/text")
    suspend fun postTypingLetter(
        @Part("letterTextReqDto") postTypingLetterRequest: PostTypingLetterRequest,
        @Part content: MultipartBody.Part,
        @Part files: List<MultipartBody.Part>?
    ): Response<BusinessResponse>

    // 토큰 재발급
    @GET("/user/token")
    suspend fun getReAccessToken(@Query("refreshToken") refreshToken: String): Response<AuthResponse>

    // 유저 검색
    @GET("/user/search")
    suspend fun searchUser(
        @Query("id") id: String,
        @Query("limit") limit: Int,
        @Query("checkFriends") checkFriends: Boolean
    ): Response<SearchUserResponse>

    // 캐릭터 도감 목록 조회
    @GET("/store/crowtit")
    suspend fun getCharacterEncyclopediaList(): Response<CharacterEncyclopediaListResponse>

    // 편지 상세 보기
    @GET("letter/{letter_id}")
    suspend fun getLetter(
        @Path("letter_id") letterId: Int
    ): Response<ReceivedLetterDetailResponse>

    // 상점 캐릭터 리스트 조회
    @GET("/store/crowtit/all")
    suspend fun getStoreCharacterList(): Response<StoreCharacterListResponse>

    // 상점 편지 리스트 조회
    @GET("/store/letterpaper/all")
    suspend fun getStoreItemLetterPaperList(): Response<StoreItemLetterPaperListResponse>

    // 상점 캐릭터 구매
    @POST("/store/crowtit/buy")
    suspend fun buyStoreCharacter(@Body buyStoreCharacterRequest: BuyStoreCharacterRequest): Response<BuyStoreCharacterResponse>

    @POST("/store/letterpaper/buy")
    suspend fun buyStoreLetterPaper(@Body buyStoreLetterPaperRequest: BuyStoreLetterPaperRequest): Response<BuyStoreLetterPaperResponse>
    
    // 미확인 편지 리스트 수신
    @GET("/letter/unchecked")
    suspend fun getUncheckedLetter(): Response<UncheckedLetterResponse>
    
    // 편지 place 읽음 처리 == (AR로 찾은 편지 요청)
    @GET("/letter/{letter_id}")
    suspend fun getReadLetter(
        @Path("letter_id") letter_id: Int,
    )
    
}



