package com.project.githubuser.repository

import com.project.githubuser.api.RetrofitClient
import com.project.githubuser.model.detail.DetailUserResponse
import com.project.githubuser.model.repos.Repos
import com.project.githubuser.model.user.User
import com.project.githubuser.model.user.UserResponse
import retrofit2.Call

object UserRepository {

    private val instance = RetrofitClient.instance

    fun getUsers(query: String): Call<UserResponse> =
        instance.getUsers(query)

    fun getDetailUser(username: String): Call<DetailUserResponse> =
        instance.getDetailUser(username)

    fun getRepos(username: String): Call<MutableList<Repos>> =
        instance.getRepos(username)

    fun getFollowers(username: String): Call<MutableList<User>> =
        instance.getFollowers(username)

    fun getFollowing(username: String): Call<MutableList<User>> =
        instance.getFollowing(username)
}