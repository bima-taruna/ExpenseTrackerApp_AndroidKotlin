package com.bima.expensetrackerapp.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bima.expensetrackerapp.domain.repository.TabDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ui_mode_preference")
class TabDataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : TabDataStoreRepository {

    override suspend fun setTabIndex(value: Int) {
        val preferencesUiKey = intPreferencesKey("tabIndex")
        context.dataStore.edit { pref->
            pref[preferencesUiKey] = value
        }
    }

    override suspend fun getTabIndex(key: String): Flow<Int> {
        val preferencesUiKey = intPreferencesKey("tabIndex")
        return context.dataStore.data.map { pref ->
            pref[preferencesUiKey] ?: 0
        }
    }
}