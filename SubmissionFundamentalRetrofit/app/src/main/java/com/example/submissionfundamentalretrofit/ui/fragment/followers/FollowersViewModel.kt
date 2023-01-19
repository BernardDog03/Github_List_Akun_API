package com.example.submissionfundamentalretrofit.ui.fragment.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionfundamentalretrofit.api.ApiConfig
import com.example.submissionfundamentalretrofit.api.ApiService
import com.example.submissionfundamentalretrofit.data.Repository
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val _list = MutableLiveData<ArrayList<UserResponseItem>>()
    val list: LiveData<ArrayList<UserResponseItem>> = _list

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    fun setFollower(username: String){
        val client = ApiService.getApiService().getFollowerDetails(username)
        client.enqueue(object: Callback<ArrayList<UserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _list.postValue(response.body())
                } else {
                    _toastMsg.value = "Request Time Out" + response.message()
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                _toastMsg.value = "Request Time Out " + t.message.toString()
            }
        })
    }
}