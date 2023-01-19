package com.example.submissionfundamentalretrofit.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionfundamentalretrofit.data.Repository
import com.example.submissionfundamentalretrofit.ui.activity.detail.DetailViewModel
import com.example.submissionfundamentalretrofit.ui.fragment.favorite.FavoriteViewModel
import com.example.submissionfundamentalretrofit.ui.fragment.followers.FollowersViewModel
import com.example.submissionfundamentalretrofit.ui.fragment.following.FollowingViewModel
import com.example.submissionfundamentalretrofit.ui.fragment.home.HomeViewModel
import com.example.submissionfundamentalretrofit.ui.fragment.setting.SettingViewModel
import com.example.submissionfundamentalretrofit.utils.Injection

class FactoryViewModel(private val repository: Repository) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(SettingViewModel::class.java)){
            return SettingViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: FactoryViewModel? = null
        fun getInstance(context: Context): FactoryViewModel = instance?: synchronized(this){
            instance?: FactoryViewModel(Injection.provideRepository(context))
        }. also {
            instance = it
        }
    }
}