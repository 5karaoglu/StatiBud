package com.example.contestifyfirsttry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spotify.android.appremote.api.SpotifyAppRemote

import com.spotify.sdk.android.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.artists_fragment.*

import kotlinx.android.synthetic.main.tracks_fragment.*
import okhttp3.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()

        button.setOnClickListener { refresh() }
    }

    fun refresh(){

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

    /*fun getProfile(){
       var request:Request =  Request.Builder()
           .url("https://api.spotify.com/v1/me/top/artists")
           .addHeader("Authorization","Bearer $token")
           .build()


        if(mCall!=null){
            mCall!!.cancel()
        }
        mCall = mOkHttpClient.newCall(request)

        mCall!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                textView.text = e.message
                Log.d(TAG, "onFailure: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    var jsonObject = JSONObject(response.body()!!.string())
                    textView.text = jsonObject.toString()
                    Log.d(TAG, "onResponse: ${jsonObject}")
                } catch (e: JSONException) {
                    textView.text = e.message
                    Log.d(TAG, "onResponse: ${e.message}")
                }

            }
        })
    }*/

}