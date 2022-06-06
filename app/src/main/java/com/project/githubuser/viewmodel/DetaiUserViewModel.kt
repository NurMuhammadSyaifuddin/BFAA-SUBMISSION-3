package com.project.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.database.UserFavorite
import com.project.githubuser.model.detail.DetailUserResponse
import com.project.githubuser.model.user.User
import com.project.githubuser.repository.UserFavoriteRepository
import com.project.githubuser.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val detailUser = MutableLiveData<DetailUserResponse>()

    private val userFavoriteRepository = UserFavoriteRepository(application)

    private var toast: ((String) -> Unit)? = null

    fun setDetailUser(username: String) {
        UserRepository
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                    toast?.let { it(t.message.toString()) }
                }

            })

    }

    fun getDetailUser(): LiveData<DetailUserResponse> = detailUser

    fun insert(username: String, id: Int, avatarUrl: String) {
        val userFavorite = UserFavorite(id, avatarUrl, username)
        userFavoriteRepository.insert(userFavorite)
    }

    suspend fun delete(user: User): Int {
        return userFavoriteRepository.delete(user)
    }

    suspend fun checkedUser(user: User): Int {
        return userFavoriteRepository.checkedUser(user)
    }

    fun showToast(toast: ((String) -> Unit)){
        this.toast = toast
    }

    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }

}