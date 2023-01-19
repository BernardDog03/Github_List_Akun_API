package com.example.submissionfundamentalretrofit.adapter

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingAdapter private constructor(private val dataStore: DataStore<Preferences>){

    private val themeKey = booleanPreferencesKey("theme_setting")

    fun themeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey]?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingAdapter? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingAdapter {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingAdapter(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}