package com.example.contestifyfirsttry.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(fragmentManager: FragmentManager, private val titleArray: ArrayList<String>, private val tabArray: ArrayList<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getPageTitle(position: Int): CharSequence {
        return titleArray[position]
    }
    override fun getCount(): Int {
        return tabArray.size
    }
    override fun getItem(position: Int): Fragment {
        return tabArray[position]
    }
}