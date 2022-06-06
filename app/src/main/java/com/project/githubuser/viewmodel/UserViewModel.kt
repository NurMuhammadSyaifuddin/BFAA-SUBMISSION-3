package com.project.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.project.githubuser.model.user.User
import com.project.githubuser.model.user.UserResponse
import com.project.githubuser.preference.SettingPreferences
import com.project.githubuser.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {

    private val listUsers = MutableLiveData<MutableList<User>>()
    private var toast: ((String) -> Unit)? = null

    fun setUsers(query: String?) {
        query?.let {
            UserRepository
                .getUsers(it)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            listUsers.postValue(response.body()?.items)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message.toString()}")
                        toast?.let { it1 -> it1(t.message.toString()) }
                    }

                })
        }

    }

    fun getUsers(): LiveData<MutableList<User>> = listUsers

    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch { settingPreferences.saveThemeSetting(isDarkMode) }
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return settingPreferences.getThemeSetting().asLiveData()
    }

    fun showToast(toast: ((String) -> Unit)){
        this.toast = toast
    }

    companion object {
        private val TAG = UserViewModel::class.java.simpleName
    }

}