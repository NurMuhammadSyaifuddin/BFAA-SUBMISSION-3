package com.project.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.database.UserFavorite
import com.project.githubuser.repository.UserFavoriteRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val userFavoriteRepository = UserFavoriteRepository(application)

    fun getAllUserFavorite(): LiveData<List<UserFavorite>> =
        userFavoriteRepository.getAllUserFavorite()

}