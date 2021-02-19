package com.example.contestifyfirsttry.load

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.main.MainActivity
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse

class ContentActivity : AppCompatActivity() {
    private var TAG = "Content Activity"

    private val CLIENT_ID = "85e82d6c52384d2b9ada66f99f78648c"
    private val REDIRECT_URI = "http://com.example.contestifyfirsttry/callback"
    val REQUEST_CODE = 1337

    private var factory: CustomViewModelFactory? = null
    private var viewmodel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        // ViewModel components
        factory = CustomViewModelFactory(this,this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        viewmodel!!.getToken(this)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQUEST_CODE){
            var response = AuthorizationClient.getResponse(resultCode,data)
            when(response.type){
                AuthorizationResponse.Type.TOKEN -> {
                    Log.d(TAG, "onActivityResult: ${response.accessToken}")
                    var intent = Intent(this, MainActivity::class.java)
                    val sharedPreferences = this.getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token",response.accessToken)
                    editor.apply()
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}