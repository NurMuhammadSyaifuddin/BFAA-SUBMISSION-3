package com.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.model.repos.Repos
import com.project.githubuser.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReposViewModel : ViewModel() {

    private val listRepos = MutableLiveData<MutableList<Repos>>()
    private var toast: ((String) -> Unit)? = null

    fun setRepos(username: String) {
        UserRepository
            .getRepos(username)
            .enqueue(object : Callback<MutableList<Repos>> {
                override fun onResponse(
                    call: Call<MutableList<Repos>>,
                    response: Response<MutableList<Repos>>
                ) {
                    if (response.isSuccessful) {
                        listRepos.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<Repos>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                    toast?.let { it(t.message.toString()) }
                }

            })
    }

    fun getRepos(): LiveData<MutableList<Repos>> = listRepos

    fun showToast(toast: ((String) -> Unit)){
        this.toast = toast
    }

    companion object {
        private val TAG = ReposViewModel::class.java.simpleName
    }

}