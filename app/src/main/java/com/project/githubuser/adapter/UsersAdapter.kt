package com.project.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.githubuser.databinding.ItemUserBinding
import com.project.githubuser.model.user.User
import com.project.githubuser.utils.DivUsersCallback
import com.project.githubuser.utils.loadImage

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var listener: ((User) -> Unit)? = null

    var users = mutableListOf<User>()
        set(value) {
            val diffCallback = DivUsersCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, listener: ((User) -> Unit)?) {

            binding.apply {
                avatarUser.loadImage(user.avatar)
                tvUsername.text = user.username
            }

            listener?.let {
                itemView.setOnClickListener {
                    listener(user)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position], listener)
    }

    override fun getItemCount(): Int = users.size

    fun onClick(listener: ((User) -> Unit)?) {
        this.listener = listener
    }

}