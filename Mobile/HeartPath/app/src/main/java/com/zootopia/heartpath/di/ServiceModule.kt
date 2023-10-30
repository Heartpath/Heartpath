package com.zootopia.heartpath.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zootopia.data.service.BusinessService
import com.zootopia.data.service.NaverService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    private const val BUSINESS_BASE_URL = "https://naveropenapi.apigw.ntruss.com" // Business Base Url
    private const val NAVER_BASE_URL = "https://naveropenapi.apigw.ntruss.com" // Naver Base Url
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
//        .addInterceptor(AuthInterceptor()) // TODO interceptor 추가 필요 (주석 해제)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    
    @Singleton
    @Provides
    fun provideBussinessRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
        
        return Retrofit.Builder()
            .baseUrl(BUSINESS_BASE_URL) // TODO BASE_URL 추가 필요
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    
    
    @Singleton
    @Provides
    @Named("naverRetrofit")
    fun provideNaverRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
        
        return Retrofit.Builder()
            .baseUrl(NAVER_BASE_URL) // TODO BASE_URL 추가 필요
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    
    @Singleton
    @Provides
    fun provideBusinessService (
        retrofit: Retrofit,
    ): BusinessService = retrofit.create(BusinessService::class.java)
    
    @Singleton
    @Provides
    fun provideNaverService (
        @Named("naverRetrofit") retrofit: Retrofit,
    ): NaverService = retrofit.create(NaverService::class.java)
}