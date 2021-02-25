package com.uhi5d.spotibud.load

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.main.MainActivity
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.util.CustomViewModelFactory
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {
    private var TAG = "Content Activity"


    private val REQUEST_CODE = 1337

    private var factory: CustomViewModelFactory? = null
    private var viewmodel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        // ViewModel components
        factory = CustomViewModelFactory(this,this)
        viewmodel = ViewModelProvider(this, factory!!).get(MainViewModel::class.java)

        button.setOnClickListener {
            viewmodel!!.getToken(this)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQUEST_CODE){
            val response = AuthorizationClient.getResponse(resultCode,data)
            when(response.type){
                AuthorizationResponse.Type.TOKEN -> {
                    Log.d(TAG, "onActivityResult: ${response.accessToken}")
                    val intent = Intent(this, MainActivity::class.java)
                    val sharedPreferences = this.getSharedPreferences("spotifystatsapp",Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token",response.accessToken)
                    editor.apply()
                    startActivity(intent)
                    finish()
                }
                else -> Toast.makeText(
                    this,
                    response.error,
                    Toast.LENGTH_SHORT
                )
                    .show()

            }
        }
    }
}