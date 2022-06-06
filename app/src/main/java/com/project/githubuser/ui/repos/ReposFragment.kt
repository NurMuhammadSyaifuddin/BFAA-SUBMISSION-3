package com.project.githubuser.ui.repos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.R
import com.project.githubuser.adapter.ReposAdapter
import com.project.githubuser.databinding.FragmentReposBinding
import com.project.githubuser.viewmodel.ReposViewModel
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.utils.gone
import com.project.githubuser.utils.visible

class ReposFragment : Fragment() {

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding as FragmentReposBinding
    private lateinit var reposAdapter: ReposAdapter
    private lateinit var reposViewModel: ReposViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReposBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // init
        reposAdapter = ReposAdapter()
        reposViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ReposViewModel::class.java]

        getRepos()

    }

    private fun getRepos() {

        showLoading(true)

        binding.rvRepos.adapter = reposAdapter

        val username = arguments?.getString(DetailActivity.EXTRA_USER).toString()

        reposViewModel.also {
            it.showToast { message ->
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
            it.setRepos(username)
            it.getRepos().observe(viewLifecycleOwner) { repos ->
                if (repos != null) {
                    reposAdapter.repos = repos
                    showLoading(false)
                } else {
                    showLoading(false)
                    Toast.makeText(activity, getString(R.string.emptyData), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                pbRepos.visible()
                rvRepos.gone()
            }
        } else {
            binding.apply {
                pbRepos.gone()
                rvRepos.visible()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}