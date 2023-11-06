package com.zootopia.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getPermissionRejected(key: String) : Flow<Int>
    suspend fun setPermissionRejected(key : String, stack: Int)
    fun getBgmState(key: String) : Flow<Boolean>
    suspend fun setBgmState(key : String, stateValue: Boolean)
}