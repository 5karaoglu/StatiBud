package com.example.contestifyfirsttry.detailed

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.contestifyfirsttry.MainViewModel
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.top.ViewPagerAdapter
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item_detailed.*
import kotlinx.android.synthetic.main.fragment_item_detailed.tabLayoutFragmentTop

class DetailedArtistFragment : Fragment(){
    private val TAG = "Detailed Fragment"
    private lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting currentId
        val sharedPreferences = requireActivity().getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token","")

        val bundle = requireArguments()
        val name = bundle.get("name") as String
        val id = bundle.get("id") as String
        val image = bundle.get("image") as String
        setData(name,image)
        initViewPager()
    }

    fun setData(name:String,image:String){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(imageView)

        detailedToolbar.title = name
    }
    fun initViewPager(){
        var adapter = DetailedViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        pagerDetailedArtist.adapter = adapter
        var tabList = arrayListOf<String>(getString(R.string.artist_profile),getString(R.string.artist_similar))
        TabLayoutMediator(tabLayoutFragmentTop,pagerDetailedArtist){tab, position ->
            tab.text = tabList[position]
        }.attach()
        pagerDetailedArtist.currentItem = 0
    }


}