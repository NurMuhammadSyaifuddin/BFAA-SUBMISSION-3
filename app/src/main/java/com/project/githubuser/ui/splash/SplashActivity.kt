package com.project.githubuser.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.R
import com.project.githubuser.preference.SettingPreferences
import com.project.githubuser.ui.main.MainActivity
import com.project.githubuser.viewmodel.UserViewModel
import com.project.githubuser.viewmodel.ViewModelFactory

class SplashActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val settingPreferences = SettingPreferences.getInstance(dataStore)
        val userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstanceMode(this.application, settingPreferences)
        )[UserViewModel::class.java]

        userViewModel.getThemeSetting().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        Handler(Looper.getMainLooper())
            .postDelayed({
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
                finish()
            }, DELAY_SPLASH)

    }

    companion object {
        private const val DELAY_SPLASH = 800L
    }
}