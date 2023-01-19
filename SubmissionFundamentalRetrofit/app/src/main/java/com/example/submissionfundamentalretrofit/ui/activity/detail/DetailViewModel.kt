package com.example.submissionfundamentalretrofit.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamentalretrofit.data.Repository
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: Repository): ViewModel() {

    val detailList: LiveData<UserResponseItem> = repository.detailList
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastMsg: LiveData<String> = repository.toastMsg

    fun detailsData(username: String){
        viewModelScope.apply {
            repository.setDetails(username)
        }
    }

    fun getFavUser() = repository.getFavUser()

    fun isFavUser(userEntity: UserEntity, isFavorite: Boolean){
        viewModelScope.launch {
            if (isFavorite){
                repository.deleteFavUser(userEntity, false)
            }
            else{
                repository.saveFavUser(userEntity, true)
            }
        }
    }
}