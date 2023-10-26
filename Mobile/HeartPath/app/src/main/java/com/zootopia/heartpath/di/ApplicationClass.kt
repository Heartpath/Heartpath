package com.zootopia.heartpath.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        
//        KakaoSdk.init(this, "db2ceb8eb8e28b57ce891b7f53534870")
    }
}
