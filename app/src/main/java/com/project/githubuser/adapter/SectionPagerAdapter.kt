package com.project.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.githubuser.ui.followers.FollowersFragment
import com.project.githubuser.ui.following.FollowingFragment
import com.project.githubuser.ui.repos.ReposFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val fragmentBundle: Bundle?) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            0 -> ReposFragment()
            1 -> FollowersFragment()
            else -> FollowingFragment()
        }

        fragment.arguments = fragmentBundle

        return fragment
    }

}