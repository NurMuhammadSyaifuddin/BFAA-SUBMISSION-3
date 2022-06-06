package com.project.githubuser.model.user


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User(
    val id: Int,
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("login")
    val username: String
) : Parcelable