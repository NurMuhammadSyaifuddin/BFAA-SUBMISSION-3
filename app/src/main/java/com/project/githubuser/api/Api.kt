package com.project.githubuser.api

import com.project.githubuser.BuildConfig
import com.project.githubuser.model.detail.DetailUserResponse
import com.project.githubuser.model.repos.Repos
import com.project.githubuser.model.user.User
import com.project.githubuser.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token $API_KEY")
    fun getUsers(@Query("q") query: String): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/repos")
    @Headers("Authorization: token $API_KEY")
    fun getRepos(@Path("username") username: String): Call<MutableList<Repos>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_KEY")
    fun getFollowers(@Path("username") username: String): Call<MutableList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_KEY")
    fun getFollowing(@Path("username") username: String): Call<MutableList<User>>

    companion object {
        private const val API_KEY = BuildConfig.API_KEY
    }

}