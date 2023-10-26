package com.zootopia.data.repository

import com.zootopia.data.datasource.local.PreferenceDataSource
import com.zootopia.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "PreferenceRepositoryImp_HeartPath"

class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource,
) : PreferenceRepository {
    
    override fun getPermissionRejected(key: String): Flow<Int> {
        return preferenceDataSource.getPermissionRejected(key)
    }
    
    override suspend fun setPermissionRejected(key: String, stack: Int) {
        preferenceDataSource.setPermissionRejected(key, stack)
    }
    
}