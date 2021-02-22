package com.example.contestifyfirsttry.top

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.home.HomePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_top.*


class TopFragment : Fragment() {
    private lateinit var adapter : ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewPager()
        initExit()
    }
    private fun initExit(){
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.dialog_text)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_accept
                ) { dialog, which -> requireActivity().finish() }
                .setNegativeButton(R.string.dialog_deny,null)
                .show()
        }
        callback.isEnabled = true
    }
    private fun initViewPager(){
        val fList = arrayListOf<Fragment>(ShortTermFragment(), MidTermFragment(), LongTermFragment())
        var titleList = arrayListOf<String>(getString(R.string.top_short_term),getString(R.string.top_mid_term),getString(R.string.top_long_term))
        val adapter = ViewPagerAdapter(childFragmentManager,fList,titleList)
        pagerTop.adapter = adapter
        pagerTop.offscreenPageLimit = 3
        tabLayoutTop.setupWithViewPager(pagerTop)
    }

}