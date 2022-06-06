package com.project.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.project.githubuser.R
import com.project.githubuser.adapter.SectionPagerAdapter
import com.project.githubuser.databinding.ActivityDetailBinding
import com.project.githubuser.viewmodel.DetailUserViewModel
import com.project.githubuser.model.user.User
import com.project.githubuser.utils.loadImage
import com.project.githubuser.viewmodel.ViewModelFactory
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private var bundle: Bundle? = null

    private var isChecked = false

    private lateinit var user: User

    private val dispatchers = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatchers)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        bundle = Bundle()
        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[DetailUserViewModel::class.java]
        sectionPagerAdapter = SectionPagerAdapter(this, bundle)

        getDataUser()

        onAction()

        sendBundle()
        getPages()

        getIsCheckedFavorite()

    }

    private fun getIsCheckedFavorite() {

        scope.launch {

            val count = detailUserViewModel.checkedUser(user)

            withContext(Dispatchers.Main) {
                if (count > 0) {
                    binding.btnFavorite.isChecked = true
                    isChecked = true
                } else {
                    binding.btnFavorite.isChecked = false
                    isChecked = false
                }
            }

        }

    }

    private fun getPages() {
        binding.apply {
            vpDetail.adapter = sectionPagerAdapter

            TabLayoutMediator(tabLayout, vpDetail) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun sendBundle() {
        user = intent?.getParcelableExtra<User>(EXTRA_USER) as User
        bundle?.putString(EXTRA_USER, user.username)
    }

    private fun onAction() {

        binding.apply {

            imgClose.setOnClickListener { finish() }

            btnFavorite.setOnClickListener {
                isChecked = !isChecked
                getIsCheckedClicked(isChecked)
            }

        }

    }

    private fun getIsCheckedClicked(isChecked: Boolean) {
        binding.apply {
            scope.launch {
                if (isChecked) {
                    detailUserViewModel.insert(user.username, user.id, user.avatar)
                    showSnackBar(getString(R.string.insert_user_favorite, user.username))
                } else {
                    detailUserViewModel.delete(user)
                    showSnackBar(getString(R.string.delete_user_favorite, user.username))
                }
                withContext(Dispatchers.Main) {
                    binding.btnFavorite.isChecked = isChecked
                }
            }
        }
        this.isChecked = isChecked
    }

    private fun getDataUser() {

        intent?.let {
            user = it.getParcelableExtra<User>(EXTRA_USER) as User

            val username = user.username

            detailUserViewModel.showToast { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            detailUserViewModel.setDetailUser(username)
            getDetailUser()

        }
    }

    private fun getDetailUser() {

        detailUserViewModel.getDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    avatarUser.loadImage(it.avatar)
                    tvUsername.text = it.username
                    tvName.text = it.name
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvRepository.text = it.repository.toString()
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                }
            }
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.vpDetail, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(R.string.tab1, R.string.tab2, R.string.tab3)
    }

}