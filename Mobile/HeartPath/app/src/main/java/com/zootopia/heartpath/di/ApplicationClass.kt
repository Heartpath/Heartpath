package com.zootopia.heartpath.di

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.zootopia.heartpath.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        disableDarkMode()
        KakaoSdk.init(this, "${BuildConfig.KAKAO_NATIVE_APP_KEY}")
    }
    
    // 다크모드 x
    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
