package com.example.submissionfundamentalretrofit.ui.fragment.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submissionfundamentalretrofit.data.Repository
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: Repository) : ViewModel() {

    val list : LiveData<ArrayList<UserResponseItem>> = repository.list
    val toastMsg: LiveData<String> = repository.toastMsg

    fun getThemeSetting(): LiveData<Boolean>{
        return repository.themeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkMode: Boolean){
        viewModelScope.launch {
            repository.savedThemeSetting(isDarkMode)
        }
    }
}