package com.zootopia.heartpath.di

import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.data.datasource.remote.business.BusinessDataSource
import com.zootopia.data.datasource.remote.letter.LetterDataSource
import com.zootopia.data.datasource.remote.login.LoginDataSource
import com.zootopia.data.datasource.remote.map.MapDataSource
import com.zootopia.data.datasource.remote.user.UserDataSource
import com.zootopia.data.repository.PreferenceRepositoryImpl
import com.zootopia.data.repository.business.BusinessRepositoryImpl
import com.zootopia.data.repository.letter.SendLetterRepositoryImpl
import com.zootopia.data.repository.login.LoginRepositoryImpl
import com.zootopia.data.repository.map.MapRepositoryImpl
import com.zootopia.data.repository.user.UserRepositoryImpl
import com.zootopia.domain.repository.PreferenceRepository
import com.zootopia.domain.repository.business.BusinessRepository
import com.zootopia.domain.repository.letter.SendLetterRepository
import com.zootopia.domain.repository.login.LoginRepository
import com.zootopia.domain.repository.map.MapRepository
import com.zootopia.domain.repository.user.UserRepository
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
    fun provideMapRepository(mapDataSource: MapDataSource): MapRepository {
        return MapRepositoryImpl(mapDataSource)
    }

    @Singleton
    @Provides
    fun provideBusinessRepository(businessDataSource: BusinessDataSource): BusinessRepository {
        return BusinessRepositoryImpl(businessDataSource)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource, preferenceDataSource: PreferenceDataSource): LoginRepository {
        return LoginRepositoryImpl(loginDataSource = loginDataSource, preferenceDataSource = preferenceDataSource)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource = userDataSource)
    }

    @Singleton
    @Provides
    fun provideLetterRepository(letterDataSource: LetterDataSource): SendLetterRepository {
        return SendLetterRepositoryImpl(letterDataSource = letterDataSource)
    }
}
