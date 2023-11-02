package com.zootopia.heartpath.di

import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.datasource.remote.map.MapDataSourceImpl
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
        tmapService: TmapService
    ): MapDataSource {
        return MapDataSourceImpl(
            naverService = naverService,
            tmapService = tmapService
        )
    }
}