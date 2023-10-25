package com.zootopia.data.repository

import com.zootopia.data.datasource.local.PreferenceDataSource
import javax.inject.Inject

private const val TAG = "PreferenceRepositoryImp_HeartPath"

class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceDataSource: PreferenceDataSource
){
}