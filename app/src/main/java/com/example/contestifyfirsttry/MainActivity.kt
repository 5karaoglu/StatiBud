package com.example.contestifyfirsttry


import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.AdManagerAdViewOptions
import com.google.firebase.analytics.FirebaseAnalytics
import com.spotify.sdk.android.auth.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.artists_fragment.*
import kotlinx.android.synthetic.main.tracks_fragment.*
import okhttp3.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var navController: NavController? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAdView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //ad section
        MobileAds.initialize(this)
        setAd()

        //navigation and bottom navigation
        navController = findNavController(R.id.fragment)
        bottomNavSetup()


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        bottomBar.setupWithNavController(menu!!, navController!!)
        return true
    }

    private fun bottomNavSetup(){

        bottomBar.onItemSelected = {
            Log.d(TAG, "onItemSelected:$it ")
            when(it){
                0 -> navController!!.navigate(R.id.homeFragment)
                1 -> navController!!.navigate(R.id.topFragment)
                2 -> navController!!.navigate(R.id.searchFragment)
                3 -> navController!!.navigate(R.id.profileFragment)
            }
        }
        bottomBar.onItemReselected = {
            Log.d(TAG, "onItemRe-Selected:$it ")
            when(it){
                0 -> navController!!.navigate(R.id.homeFragment)
                1 -> navController!!.navigate(R.id.topFragment)
                2 -> navController!!.navigate(R.id.searchFragment)
                3 -> navController!!.navigate(R.id.profileFragment)
        }}
    }
    private fun setAd(){
        mAdView = findViewById<View>(R.id.adView) as AdView?
        val adRequest: AdRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
    }





}