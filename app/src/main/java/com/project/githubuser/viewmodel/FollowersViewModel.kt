package com.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.model.user.User
import com.project.githubuser.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val listFollowers = MutableLiveData<MutableList<User>>()
    private var toast: ((String) -> Unit)? = null

    fun setFollowers(username: String) {
        UserRepository
            .getFollowers(username)
            .enqueue(object : Callback<MutableList<User>> {
                override fun onResponse(
                    call: Call<MutableList<User>>,
                    response: Response<MutableList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                    toast?.let { it(t.message.toString()) }
                }

            })
    }

    fun getFollowers(): LiveData<MutableList<User>> = listFollowers

    fun showToast(toast: ((String) -> Unit)){
        this.toast = toast
    }

    companion object {
        private val TAG = FollowersViewModel::class.java.simpleName
    }

}