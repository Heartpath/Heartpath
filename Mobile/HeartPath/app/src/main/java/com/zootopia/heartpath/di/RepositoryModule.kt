package com.zootopia.heartpath.di

import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.repository.PreferenceRepositoryImpl
import com.zootopia.domain.repository.PreferenceRepository
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
}