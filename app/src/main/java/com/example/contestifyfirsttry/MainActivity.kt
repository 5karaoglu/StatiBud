package com.example.contestifyfirsttry

import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.model.User
import com.spotify.android.appremote.api.SpotifyAppRemote

import com.spotify.sdk.android.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.artists_fragment.*

import kotlinx.android.synthetic.main.tracks_fragment.*
import okhttp3.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var viewModel:MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
        setUsername()
    }


    fun initViewPager(){
        var adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        pager.adapter = adapter
         var tabList = arrayListOf<String>("Artists","Tracks")
        TabLayoutMediator(tabLayout,pager){tab, position ->
          tab.text = tabList[position]
        }.attach()
        pager.currentItem = 0
    }
    private fun setUsername(){
        var username:String? = null

        var bundle = this.intent.extras
        var token = bundle!!.getString("token")
        viewModel = MainViewModel(this)

        viewModel!!.user.observe(this,
            Observer<User> { t -> tvUserName.text = t.display_name.toString() + " sup" })

        viewModel!!.getUser(token!!)
    }

}