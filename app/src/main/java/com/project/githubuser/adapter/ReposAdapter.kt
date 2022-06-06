package com.project.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.githubuser.databinding.ItemReposBinding
import com.project.githubuser.model.repos.Repos
import com.project.githubuser.utils.DivReposCallback

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    var repos = mutableListOf<Repos>()
        set(value) {
            val diffCallback = DivReposCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    class ViewHolder(private val binding: ItemReposBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repos: Repos) {
            binding.apply {
                tvUrlRepos.text = repos.name
                tvDescriptionRepos.text = repos.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReposBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int = repos.size
}