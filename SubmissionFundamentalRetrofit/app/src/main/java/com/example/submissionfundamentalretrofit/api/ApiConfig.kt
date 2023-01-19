package com.example.submissionfundamentalretrofit.api

import retrofit2.Call
import com.example.submissionfundamentalretrofit.data.remote.UserResponse
import com.example.submissionfundamentalretrofit.data.remote.UserResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiConfig {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String?
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetails(
        @Path("username") userName: String
    ): Call<UserResponseItem>

    @GET("users/{username}/following")
    fun getFollowingDetails(
        @Path("username") userName: String
    ): Call<ArrayList<UserResponseItem>>

    @GET("users/{username}/followers")
    fun getFollowerDetails(
        @Path("username") userName: String
    ): Call<ArrayList<UserResponseItem>>
}