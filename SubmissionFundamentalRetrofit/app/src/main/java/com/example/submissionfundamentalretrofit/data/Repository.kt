package com.example.submissionfundamentalretrofit.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.submissionfundamentalretrofit.adapter.SettingAdapter
import com.example.submissionfundamentalretrofit.api.ApiConfig
import com.example.submissionfundamentalretrofit.data.local.UserDao
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.data.remote.UserResponse
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(

    private val apiService: ApiConfig,
    private val preferences: SettingAdapter,
    private val userDao: UserDao
){

    private val _list = MutableLiveData<ArrayList<UserResponseItem>>()
    val list: LiveData<ArrayList<UserResponseItem>> = _list

    private val _detailList = MutableLiveData<UserResponseItem>()
    val detailList: LiveData<UserResponseItem> = _detailList

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(query: String?){
        _isLoading.value = true
        val client = apiService.getSearchUser(query)
        client.enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _list.value = response.body()?.UserResponse
                }
                else{
                    _toastMsg.value = "Request Time Out: " +response.message()
                    Log.e(TAG, "onFailure:  ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _toastMsg.value = "Request Time Out: "+t.message.toString()
            }
        })
    }

    fun setDetails(username: String){
        _isLoading.value = true

        val client = apiService.getUserDetails(username)
        client.enqueue(object : Callback<UserResponseItem>{
            override fun onResponse(
                call: Call<UserResponseItem>,
                response: Response<UserResponseItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailList.value = response.body()
                }
                else{
                    _toastMsg.value = "Request Time Out: "+ response.message()
                }
            }

            override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                _isLoading.value = false
                _toastMsg.value = "Request Time Out: "+ t.message.toString()
            }
        })
    }

    fun themeSetting(): Flow<Boolean> = preferences.themeSetting()


    suspend fun savedThemeSetting(isDarkModeActive: Boolean){
        preferences.saveThemeSetting(isDarkModeActive)
    }

    fun getFavUser(): LiveData<List<UserEntity>>{
        return userDao.getFavoriteUser().asLiveData()
    }
    suspend fun deleteFavUser(userEntity: UserEntity, state: Boolean){
        userEntity.isFavorite = state
        userDao.delete(userEntity)
    }

    suspend fun saveFavUser(userEntity: UserEntity, state: Boolean){
        userEntity.isFavorite = state
        userDao.insert(userEntity)
    }

    companion object{
        private const val TAG = "Repository"

        @Volatile
        private var instance: Repository?= null
        fun getInstance(
            apiService: ApiConfig,
            preferences: SettingAdapter,
            userDao: UserDao
        ): Repository = instance?: synchronized(this){
            instance?: Repository(apiService, preferences, userDao)
        }.also {
            instance = it
        }
    }
}