package com.zootopia.heartpath.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.intercepter.RequestInterceptor
import com.zootopia.data.intercepter.ResponseInterceptor
import com.zootopia.data.service.BusinessService
import com.zootopia.data.service.NaverService
import com.zootopia.data.service.TmapService
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
    private const val BUSINESS_BASE_URL = "https://www.heartpath.site" // Business Base Url
    private const val NAVER_BASE_URL = "https://naveropenapi.apigw.ntruss.com" // Naver Base Url
    private const val TMAP_BASE_URL = "https://apis.openapi.sk.com" // TMAP Base Url

    @Singleton
    @Provides
    fun provideOkHttpClient(preferenceDataSource: PreferenceDataSource): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
//        .addInterceptor(AuthInterceptor(preferenceDataSource)) // TODO interceptor 추가 필요 (주석 해제)
//        .addInterceptor(ResponseInterceptor(preferenceDataSource))
        .addInterceptor(RequestInterceptor(preferenceDataSource))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Singleton
    @Provides
    @Named("business")
    fun provideBussinessRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder()
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
        val gson: Gson = GsonBuilder()
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
    @Named("TmapRetrofit")
    fun provideTmapRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(TMAP_BASE_URL) // TODO BASE_URL 추가 필요
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideBusinessService(
        @Named("business") retrofit: Retrofit,
    ): BusinessService = retrofit.create(BusinessService::class.java)

    @Singleton
    @Provides
    fun provideNaverService(
        @Named("naverRetrofit") retrofit: Retrofit,
    ): NaverService = retrofit.create(NaverService::class.java)

    @Singleton
    @Provides
    fun provideTmapService(
        @Named("TmapRetrofit") retrofit: Retrofit,
    ): TmapService = retrofit.create(TmapService::class.java)
}
