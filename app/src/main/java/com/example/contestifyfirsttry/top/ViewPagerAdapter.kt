package com.example.contestifyfirsttry.top

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.contestifyfirsttry.top.ArtistsFragment
import com.example.contestifyfirsttry.top.TracksFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    private var tabList = arrayListOf<Fragment>(ArtistsFragment(), TracksFragment())

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position]
    }
}