package com.project.githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.githubuser.model.user.User

class DivUsersCallback(private val oldUserList: List<User>, private val newUserList: List<User>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldUserList.size

    override fun getNewListSize(): Int = newUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldUserList[oldItemPosition].username == newUserList[newItemPosition].username

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldUserList[oldItemPosition].username == newUserList[newItemPosition].username

}