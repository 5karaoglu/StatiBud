package com.example.contestifyfirsttry.top

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contestifyfirsttry.top.ArtistsFragment
import com.example.contestifyfirsttry.top.TracksFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, var fragmentList: ArrayList<Fragment>,titleList: ArrayList<String>): FragmentPagerAdapter(fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }
}