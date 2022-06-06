package com.project.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.adapter.UsersAdapter
import com.project.githubuser.database.UserFavorite
import com.project.githubuser.databinding.ActivityFavoriteUserBinding
import com.project.githubuser.model.user.User
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.ui.main.MainActivity
import com.project.githubuser.utils.gone
import com.project.githubuser.utils.visible
import com.project.githubuser.viewmodel.FavoriteViewModel
import com.project.githubuser.viewmodel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[FavoriteViewModel::class.java]
        usersAdapter = UsersAdapter()

        getFavoriteUser()

        getAdapter()

        onAction()
    }

    private fun onAction() {

        binding.apply {

            imgClose.setOnClickListener { finish() }

            usersAdapter.onClick {
                Intent(this@FavoriteUserActivity, DetailActivity::class.java).also { intent ->
                    intent.putExtra(DetailActivity.EXTRA_USER, it)
                    startActivity(intent)
                }
            }

            btnToSearchUser.setOnClickListener {
                Intent(this@FavoriteUserActivity, MainActivity::class.java).also {
                    it.putExtra(MainActivity.IS_FROM_FAVORITE_ACTIVITY, true)
                    startActivity(it)
                }
                finish()
            }

        }
    }

    private fun getAdapter() {
        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = usersAdapter
        }
    }

    private fun getFavoriteUser() {
        favoriteViewModel.getAllUserFavorite().observe(this) {
            if (!(it.isNullOrEmpty())) {
                val list = mapList(it)
                usersAdapter.users = list
                showNoDataFavorite(false)
            } else {
                showNoDataFavorite(true)
            }
        }
    }

    private fun mapList(users: List<UserFavorite>): MutableList<User> {
        val listUsers = mutableListOf<User>()

        for (i in users) {
            val userMapped = User(
                i.id,
                i.avatarUrl as String,
                i.username as String
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun showNoDataFavorite(state: Boolean) {
        binding.apply {
            if (state) {
                imgNoDataFavorite.visible()
                tvNoDataFavorite.visible()
                btnToSearchUser.visible()
                rvFavorite.gone()
            } else {
                imgNoDataFavorite.gone()
                tvNoDataFavorite.gone()
                btnToSearchUser.gone()
                rvFavorite.visible()
            }
        }
    }

}