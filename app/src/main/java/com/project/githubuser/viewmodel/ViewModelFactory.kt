package com.project.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.preference.SettingPreferences
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    private var settingPreferences: SettingPreferences? = null

    constructor(
        application: Application,
        settingPreferences: SettingPreferences
    ) : this(application) {
        this.settingPreferences = settingPreferences
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(application) as T
            }
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) -> {
                DetailUserViewModel(application) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(settingPreferences as SettingPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknow ViewModel class: ${modelClass.name}")
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }

        fun getInstanceMode(
            application: Application,
            settingPreferences: SettingPreferences
        ): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, settingPreferences)
                }
            }
            return INSTANCE as ViewModelFactory
        }

    }

}