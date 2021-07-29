package com.uhi5d.spotibud.application


import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
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
import com.uhi5d.spotibud.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    @Inject
    lateinit var toastHelper: ToastHelper
    private lateinit var navController: NavController
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var mAdView: AdView? = null
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_main)

        //navigation and bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu
        bottomBar.setupWithNavController(menu, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment){
                bottomBar.visibility = View.GONE
                reAd.visibility = View.GONE
            }else{
                bottomBar.visibility = View.VISIBLE
                reAd.visibility = View.VISIBLE
            }
        }


        val dialog = dialog()
        connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this,
            { isNetworkAvailable -> connectionBehaviour(isNetworkAvailable, dialog) })
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        toastHelper.toastMessages.observe(this){
            showToast(it)
        }

        //ad section
        setAd()

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