package com.example.contestifyfirsttry

import android.content.Intent
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import android.view.Menu
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.model.User
import com.spotify.android.appremote.api.SpotifyAppRemote

import com.spotify.sdk.android.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.artists_fragment.*

import kotlinx.android.synthetic.main.tracks_fragment.*
import me.ibrahimsn.lib.OnItemReselectedListener
import me.ibrahimsn.lib.OnItemSelectedListener
import okhttp3.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.fragment)

        bottomNavSetup()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu,menu)
        bottomBar.setupWithNavController(menu!!,navController!!)
        return true
    }

    private fun bottomNavSetup(){

        bottomBar.onItemSelected = {
            Log.d(TAG, "onItemSelected:$it ")
            when(it){
                0-> navController!!.navigate(R.id.topFragment)
                1-> navController!!.navigate(R.id.searchFragment)
                2-> navController!!.navigate(R.id.profileFragment)
            }
        }
        bottomBar.onItemReselected = {
            Log.d(TAG, "onItemRe-Selected:$it ")
        }
    }

}