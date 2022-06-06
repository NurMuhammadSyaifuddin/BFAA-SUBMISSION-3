package com.project.githubuser.model.detail

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatar: String,
    val name: String,
    val company: String,
    val location: String,
    @SerializedName("public_repos")
    val repository: Int,
    val followers: Int,
    val following: Int
)
