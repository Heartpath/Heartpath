package com.zootopia.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

// datastore 객체를 파일의 최상단에 올려주어 싱글톤으로 사용
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "HeartPath.pd"
)

class PreferenceDataSource @Inject constructor(
    @ApplicationContext private val context: Context, // Hilt를 사용하면서 앱 Context를 참고해야할 경우 사용해야함...
){
    private object PreferenceKeys {
        val PERMISSION_REJECTED = intPreferencesKey("permission_rejected")
    }
    
    fun getPermissionRejected(key: String): Flow<Int> {
        val rejectedPreferencesFlow: Flow<Int> = context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[intPreferencesKey(key)] ?: 0
            }
        
        return rejectedPreferencesFlow
    }
    
    suspend fun setPermissionRejected(key: String, stack: Int) {
        context.dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = stack + 1
        }
    }
}