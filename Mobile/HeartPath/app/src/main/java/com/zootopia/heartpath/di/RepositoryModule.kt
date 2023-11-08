package com.zootopia.heartpath.di

import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.datasource.remote.login.LoginDataSource
import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.repository.PreferenceRepositoryImpl
import com.zootopia.data.repository.login.LoginRepositoryImpl
import com.zootopia.data.repository.map.MapRepositoryImpl
import com.zootopia.domain.repository.PreferenceRepository
import com.zootopia.domain.repository.login.LoginRepository
import com.zootopia.domain.repository.map.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePreferenceRepository(preferenceDataSource: PreferenceDataSource): PreferenceRepository {
        return PreferenceRepositoryImpl(preferenceDataSource)
    }
    
    @Singleton
    @Provides
    fun provideMapRepository(mapDataSource: MapDataSource): MapRepository{
        return MapRepositoryImpl(mapDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource, preferenceDataSource: PreferenceDataSource): LoginRepository {
        return LoginRepositoryImpl(loginDataSource = loginDataSource, preferenceDataSource = preferenceDataSource)
    }
}