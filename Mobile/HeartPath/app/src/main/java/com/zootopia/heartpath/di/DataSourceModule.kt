package com.zootopia.heartpath.di

import com.zootopia.data.datasource.remote.business.BusinessDataSource
import com.zootopia.data.datasource.remote.business.BusinessDataSourceImpl
import com.zootopia.data.datasource.remote.letter.LetterDataSource
import com.zootopia.data.datasource.remote.letter.LetterDataSourceImpl
import com.zootopia.data.datasource.remote.login.LoginDataSource
import com.zootopia.data.datasource.remote.login.LoginDataSourceImpl
import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.datasource.remote.map.MapDataSourceImpl
import com.zootopia.data.datasource.remote.receiveletter.ReceiveLetterDataSource
import com.zootopia.data.datasource.remote.receiveletter.ReceiveLetterDataSourceImpl
import com.zootopia.data.datasource.remote.store.StoreDataSource
import com.zootopia.data.datasource.remote.store.StoreDataSourceImpl
import com.zootopia.data.datasource.remote.user.UserDataSource
import com.zootopia.data.datasource.remote.user.UserDataSourceImpl
import com.zootopia.data.service.BusinessService
import com.zootopia.data.service.NaverService
import com.zootopia.data.service.TmapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideMapDataSource(
        naverService: NaverService,
        tmapService: TmapService,
        businessService: BusinessService,
    ): MapDataSource {
        return MapDataSourceImpl(
            naverService = naverService,
            tmapService = tmapService,
            businessService = businessService,
        )
    }

    @Singleton
    @Provides
    fun provideBusinessDataSource(
        businessService: BusinessService,
    ): BusinessDataSource {
        return BusinessDataSourceImpl(businessService)
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(
        businessService: BusinessService,
    ): LoginDataSource {
        return LoginDataSourceImpl(
            businessService = businessService,
        )
    }

    @Singleton
    @Provides
    fun provideUserDataSource(
        businessService: BusinessService,
    ): UserDataSource {
        return UserDataSourceImpl(
            businessService = businessService,
        )
    }

    @Singleton
    @Provides
    fun provideLetterDataSource(
        businessService: BusinessService,
    ): LetterDataSource {
        return LetterDataSourceImpl(
            businessService = businessService,
        )
    }

    @Singleton
    @Provides
    fun provideReceiveLetterDataSource(
        businessService: BusinessService
    ): ReceiveLetterDataSource {
        return ReceiveLetterDataSourceImpl(
            businessService = businessService
        )
    }

    @Singleton
    @Provides
    fun provideStoreDataSource(
        businessService: BusinessService
    ): StoreDataSource{
        return StoreDataSourceImpl(
            businessService = businessService
        )
    }
}

