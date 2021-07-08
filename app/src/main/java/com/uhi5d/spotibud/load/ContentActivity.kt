package com.uhi5d.spotibud.load

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.main.CustomViewModelFactory
import com.uhi5d.spotibud.main.MainActivity
import com.uhi5d.spotibud.main.MainViewModel
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
            button.text = getString(R.string.button_clicked)
            button.isClickable = false
            viewmodel!!.getToken(this)
        }
        button.setTextColor(getColor(R.color.colorPrimaryDark))
        textView3.setTextColor(getColor(R.color.colorgray))
        textViewVersion.setTextColor(getColor(R.color.colorgray))
        textView2.setTextColor(getColor(R.color.colorgray2))
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==REQUEST_CODE){
            val response = AuthorizationClient.getResponse(resultCode,data)
            when(response.type){
                AuthorizationResponse.Type.TOKEN -> {
                    button.text = getString(R.string.button_response)
                    Log.d(TAG, "onActivityResult: ${response.accessToken}")
                    val intent = Intent(this, MainActivity::class.java)
                    val sharedPreferences =
                        this.getSharedPreferences("spotifystatsapp", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", response.accessToken)
                    editor.apply()
                    startActivity(intent)
                    finish()
                }
                AuthorizationResponse.Type.ERROR -> Toast.makeText(
                    this,
                    "Error: ${response.error}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                else -> {
                    Toast.makeText(
                        this,
                        "${response.error} Code: ${response.code}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.e(TAG, "onActivityResult: ${response.type}")
                    Log.e(TAG, "onActivityResult: ${response.code}")
                    Log.e(TAG, "onActivityResult: ${response.error}")
                    Log.e(TAG, "onActivityResult: ${response.accessToken}")
                    Log.e(TAG, "onActivityResult: ${response.state}")
                    Log.e(TAG, "onActivityResult: ${response.expiresIn}")

                    button.text = getString(R.string.button_start)
                    button.isClickable = true
                }

            }
        }
    }
}