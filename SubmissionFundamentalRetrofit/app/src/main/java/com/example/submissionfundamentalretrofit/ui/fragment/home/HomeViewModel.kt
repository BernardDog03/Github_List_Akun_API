package com.example.submissionfundamentalretrofit.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamentalretrofit.data.Repository
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import kotlinx.coroutines.launch
import retrofit2.http.Query

class HomeViewModel(private val repository: Repository) : ViewModel() {

    val list: LiveData<ArrayList<UserResponseItem>> = repository.list
    val isLoading: LiveData<Boolean> = repository.isLoading
    val toastMsg: LiveData<String> = repository.toastMsg

    fun searchUser(query: String){
        viewModelScope.launch {
            repository.searchUser(query)
        }
    }
}