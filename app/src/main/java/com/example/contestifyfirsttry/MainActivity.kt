package com.example.contestifyfirsttry

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spotify.android.appremote.api.SpotifyAppRemote

import com.spotify.sdk.android.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.artists_fragment.recylerArtists
import kotlinx.android.synthetic.main.tracks_fragment.*
import okhttp3.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val CLIENT_ID = "85e82d6c52384d2b9ada66f99f78648c"
    private val REDIRECT_URI = "http://com.example.contestifyfirsttry/callback"
    val REQUEST_CODE = 1337

    var scopes : Scopes? = null
    private var mCall: Call? = null
    private var mOkHttpClient: OkHttpClient = OkHttpClient()
    private var artists:Artists? = null
    private var factory:CustomViewModelFactory? = null
    private var viewmodel:MainViewModel? = null

    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    var pagerAdapter : ViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagerAdapter = ViewPagerAdapter(this)
        pager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout,pager){tab, position ->
            when(position){
                0-> tab.text = "ARTISTS"
                1-> tab.text = "TRACKS"
            }
        }.attach()

        // ViewModel components
        factory = CustomViewModelFactory(this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.artistsList.observe(this,
            Observer<Artists> { t -> generateDataArtists(t!!) })
        viewmodel!!.tracksList.observe(this,
            Observer<Tracks> { t -> generateDataTracks(t!!) })

        viewmodel!!.getToken(this)
    }



    private fun generateDataArtists(artists: Artists){
        var adapter : ArtistsAdapter = ArtistsAdapter(this,artists)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recylerArtists.layoutManager = layoutManager
        recylerArtists.adapter = adapter
    }
    private fun generateDataTracks(tracks: Tracks){
        var adapter : TracksAdapter = TracksAdapter(this,tracks)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recylerTracks.layoutManager = layoutManager
        recylerTracks.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQUEST_CODE){
            var response = AuthorizationClient.getResponse(resultCode,data)
            when(response.type){
                AuthorizationResponse.Type.TOKEN -> {
                    Log.d(TAG, "onActivityResult: ${response.accessToken}")
                    viewmodel!!.getMyArtists(response.accessToken)
                    viewmodel!!.getMyTracks(response.accessToken)

                }
            }
        }
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