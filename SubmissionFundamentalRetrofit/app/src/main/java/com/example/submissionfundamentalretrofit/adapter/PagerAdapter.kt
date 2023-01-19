package com.example.submissionfundamentalretrofit.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissionfundamentalretrofit.ui.activity.detail.DetailActivity
import com.example.submissionfundamentalretrofit.ui.fragment.followers.FollowersFragment
import com.example.submissionfundamentalretrofit.ui.fragment.following.FollowingFragment

class PagerAdapter (activity: AppCompatActivity): FragmentStateAdapter(activity) {

    var userName: String = ""

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = Bundle().apply {
            putString(FollowersFragment.ARG_NAME, userName)
            putString(FollowingFragment.ARG_NAME, userName)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return  2
    }
}