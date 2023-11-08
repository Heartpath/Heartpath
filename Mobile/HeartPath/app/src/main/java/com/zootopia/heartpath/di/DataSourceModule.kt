package com.zootopia.heartpath.di

import com.zootopia.data.datasource.remote.login.LoginDataSource
import com.zootopia.data.datasource.remote.login.LoginDataSourceImpl
import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.datasource.remote.map.MapDataSourceImpl
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
        businessService: BusinessService
    ): MapDataSource {
        return MapDataSourceImpl(
            naverService = naverService,
            tmapService = tmapService,
            businessService = businessService
        )
    }

    @Singleton
    @Provides
    fun provideLoginDataSource(
        businessService: BusinessService
    ): LoginDataSource {
        return LoginDataSourceImpl(
            businessService = businessService
        )
    }
}