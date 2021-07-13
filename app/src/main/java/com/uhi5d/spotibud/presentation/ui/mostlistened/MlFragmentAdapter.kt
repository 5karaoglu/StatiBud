package com.uhi5d.spotibud.presentation.ui.mostlistened

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MlFragmentAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> MlTracksFragment()
           1 -> MlArtistsFragment()
           else -> Fragment()
       }
    }
}