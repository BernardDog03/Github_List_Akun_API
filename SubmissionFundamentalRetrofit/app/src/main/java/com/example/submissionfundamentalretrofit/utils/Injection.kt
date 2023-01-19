package com.example.submissionfundamentalretrofit.utils

import com.example.submissionfundamentalretrofit.api.ApiService
import com.example.submissionfundamentalretrofit.data.Repository
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.submissionfundamentalretrofit.adapter.SettingAdapter
import com.example.submissionfundamentalretrofit.data.local.UserDao
import com.example.submissionfundamentalretrofit.data.local.UserDatabase

object Injection {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        "setting"
    )

    fun provideRepository(context: Context): Repository{
        val apiService = ApiService.getApiService()
        val preferences = SettingAdapter.getInstance(context.dataStore)
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()

        return Repository.getInstance(apiService, preferences, dao)
    }
}