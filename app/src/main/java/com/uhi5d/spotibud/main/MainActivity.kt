package com.uhi5d.spotibud.main


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.util.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var navController: NavController? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAdView: AdView? = null
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = dialog()
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this,
            { isNetworkAvailable -> connectionBehaviour(isNetworkAvailable, dialog) })
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        //ad section
        setAd()


        //navigation and bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavSetup()


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        bottomBar.setupWithNavController(menu!!, navController!!)
        return true
    }

    private fun bottomNavSetup(){
        bottomBar.itemIconTintActive = getColor(R.color.colorPrimaryDark)
        bottomBar.itemIconTint = getColor(R.color.colorgray2)
        bottomBar.itemIconSize = 70f
        bottomBar.barBackgroundColor = getColor(R.color.colorPrimary)
        bottomBar.barIndicatorColor = getColor(R.color.colorgray2)

        bottomBar.onItemSelected = {
            Log.d(TAG, "onItemSelected:$it ")
            when (it) {
                0 -> navController!!.navigate(R.id.homeFragment)
                1 -> navController!!.navigate(R.id.topFragment)
                2 -> navController!!.navigate(R.id.searchFragment)
                3 -> navController!!.navigate(R.id.trackFinderFragment)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    private fun setAd() {
        MobileAds.initialize(this)
        mAdView = findViewById<View>(R.id.adView) as AdView?
        val adRequest: AdRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
    }

    private fun dialog(): AlertDialog {
        return AlertDialog.Builder(this)
            .setMessage(R.string.connection_text)
            .setCancelable(false)
            .setNegativeButton(R.string.connection_negative) { _, _ -> finish() }
            .create()
    }
    private fun connectionBehaviour(isNetworkAvailable: Boolean, dialog: AlertDialog){
        if(!isNetworkAvailable){
            dialog.show()
        }else{
            dialog.dismiss()
        }

    }




}