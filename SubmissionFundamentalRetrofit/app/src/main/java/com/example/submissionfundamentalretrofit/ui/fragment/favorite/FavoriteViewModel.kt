package com.example.submissionfundamentalretrofit.ui.fragment.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionfundamentalretrofit.data.Repository
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem

class FavoriteViewModel(private val repository: Repository) : ViewModel() {

    val list: LiveData<ArrayList<UserResponseItem>> = repository.list

    fun showFavorite() = repository.getFavUser()
}