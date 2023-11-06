package com.zootopia.data.service

import retrofit2.Response
import retrofit2.http.GET

interface BusinessService {
    @GET("/user/health_check")
    suspend fun test(): Response<String>
}