package com.example.contestifyfirsttry

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var currentFragment :Fragment? = null
        when(position){
            0->currentFragment = ArtistsFragment().newInstance("Artists Fragment")
            1->currentFragment = TracksFragment().newInstance("Tracks Fragment")
        }
        return currentFragment!!
    }
}