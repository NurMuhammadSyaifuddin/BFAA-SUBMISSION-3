package com.project.githubuser.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.R
import com.project.githubuser.adapter.UsersAdapter
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.preference.SettingPreferences
import com.project.githubuser.viewmodel.UserViewModel
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.ui.favorite.FavoriteUserActivity
import com.project.githubuser.utils.*
import com.project.githubuser.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UsersAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var settingPreferences: SettingPreferences

    private var isDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        userAdapter = UsersAdapter()
        settingPreferences = SettingPreferences.getInstance(dataStore)
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstanceMode(this.application, settingPreferences)
        )[UserViewModel::class.java]

        getThemeSetting()

        showEmptyUser(true)

        isFromFavoriteUser()

        onAction()

    }

    private fun isFromFavoriteUser() {
        binding.apply {
            intent?.let { intent ->
                val isFromFavoriteActivity =
                    intent.getBooleanExtra(IS_FROM_FAVORITE_ACTIVITY, false)
                if (isFromFavoriteActivity) {
                    showSoftKeyboard(this@MainActivity, edtSearchMain)
                } else {
                    hideSoftKeyboard(this@MainActivity, binding.root)
                }
            }
        }
    }

    private fun getThemeSetting() {
        binding.apply {
            userViewModel.getThemeSetting().observe(this@MainActivity) {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.modeDark.background =
                        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_light)
                    modeDark.isChecked = true
                    isDarkMode = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.modeDark.background =
                        AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_dark)
                    modeDark.isChecked = false
                    isDarkMode = false
                }
            }
        }
    }

    private fun getDataUsers() {

        showLoading()

        userViewModel.getUsers().observe(this@MainActivity) {

            when {
                it.size == 0 -> {
                    binding.tvNoUsers.visible()
                    hideLoading()
                }
                it != null -> {
                    userAdapter.users = it
                    binding.rvUsers.adapter = userAdapter
                    hideLoading()
                }
                else -> {
                    hideLoading()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.emptyData),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onAction() {

        binding.apply {

            userAdapter.onClick { user ->
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, user)
                    startActivity(it)
                }
            }

            edtSearchMain.addTextChangedListener {

                rvUsers.adapter = null

                val result = edtSearchMain.text
                if (!(result.isNullOrEmpty())) {
                    userViewModel.showToast { message ->
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    }
                    userViewModel.setUsers(result.toString().trim())
                    getDataUsers()
                    showEmptyUser(false)
                } else {
                    hideLoading()
                    showEmptyUser(true)
                }

            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->

                val dataSearch = edtSearchMain.text.toString().trim()

                if (actionId == EditorInfo.IME_ACTION_SEARCH && dataSearch.isNotEmpty()) {
                    userViewModel.showToast { message ->
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    }
                    userViewModel.setUsers(dataSearch)
                    getDataUsers()
                    showEmptyUser(false)

                    hideSoftKeyboard(this@MainActivity, binding.root)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            favoriteUser.setOnClickListener {
                Intent(this@MainActivity, FavoriteUserActivity::class.java).also {
                    startActivity(it)
                }
            }

            modeDark.setOnClickListener {
                isDarkMode = !isDarkMode

                getModeIcon(isDarkMode)
            }

        }

    }

    private fun getModeIcon(isDarkMode: Boolean) {
        if (isDarkMode) {
            userViewModel.saveThemeSetting(true)
        } else {
            userViewModel.saveThemeSetting(false)
        }
        binding.modeDark.isChecked = isDarkMode
        this.isDarkMode = isDarkMode
    }

    private fun showLoading() {
        binding.pbMain.visible()
    }

    private fun hideLoading() {
        binding.pbMain.gone()
    }

    private fun showEmptyUser(state: Boolean) {
        binding.apply {
            if (state) {
                rvUsers.gone()
                imgNoData.visible()
                tvNoData.visible()
            } else {
                rvUsers.visible()
                imgNoData.gone()
                tvNoData.gone()
                tvNoUsers.gone()
            }
        }
    }

    companion object {
        const val IS_FROM_FAVORITE_ACTIVITY = "isFromFavoriteActivity"
    }

}