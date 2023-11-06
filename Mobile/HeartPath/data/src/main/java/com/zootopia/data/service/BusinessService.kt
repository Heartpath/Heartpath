package com.zootopia.data.service

import com.zootopia.data.model.business.request.PostHandLetterRequest
import com.zootopia.data.model.business.response.BusinessResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part

interface BusinessService {
    @GET("/user/health_check")
    suspend fun test(): Response<String>

    @POST("/letter/hand")
    suspend fun postHandLetter(
        @Part("letterHandReqDto") postHandLetterRequest: PostHandLetterRequest,
        @Part("content") content: MultipartBody.Part,
        @Part("files") files: List<MultipartBody.Part>
    ): Response<BusinessResponse>
}