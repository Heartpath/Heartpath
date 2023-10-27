package com.zootopia.heartpath.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zootopia.data.intercepter.AuthInterceptor
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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/" // TODO Url 추가 필요
    
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(AuthInterceptor()) // TODO interceptor 추가 필요 (주석 해제)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
        
        
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // TODO BASE_URL 추가 필요
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    
    @Singleton
    @Provides
    fun provideMainAPIService(
        retrofit: Retrofit
    ) : BaseService = retrofit.create(BaseService::class.java)
}