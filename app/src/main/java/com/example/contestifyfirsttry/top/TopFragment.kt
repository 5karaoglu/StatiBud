package com.example.contestifyfirsttry.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contestifyfirsttry.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_top.*


class TopFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewPager()
    }
    fun initViewPager(){
        var adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        pager.adapter = adapter
        var tabList = arrayListOf<String>("Artists","Tracks")
        TabLayoutMediator(tabLayoutFragmentTop,pager){tab, position ->
            tab.text = tabList[position]
        }.attach()
        pager.offscreenPageLimit = 2
        pager.currentItem = 0
    }


}