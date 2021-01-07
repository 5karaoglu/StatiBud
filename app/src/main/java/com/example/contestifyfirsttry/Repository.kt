package com.example.contestifyfirsttry

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import retrofit2.Callback
import retrofit2.Response

class Repository() {

    private val TAG = "Repository"

    var respArtists = MutableLiveData<Artists>()
    var respTracks = MutableLiveData<Tracks>()
    var token = MutableLiveData<String>()
    var scopes : Scopes? = null
    private val CLIENT_ID = "85e82d6c52384d2b9ada66f99f78648c"
    private val REDIRECT_URI = "http://com.example.contestifyfirsttry/callback"
    val REQUEST_CODE = 1337

    fun getToken(activity: Activity){
        scopes = Scopes()
        var request = AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
            .setScopes(arrayOf(scopes!!.USER_READ_PRIVATE, scopes!!.PLAYLIST_READ, scopes!!.PLAYLIST_READ_PRIVATE, scopes!!.USER_READ_PRIVATE,
                scopes!!.USER_TOP_READ))
            .build();

        AuthorizationClient.openLoginActivity(activity,REQUEST_CODE,request)
    }

    fun getMyFavArtists(token:String) {

        var service = RetrofitInstance().getRefrofitInstance()!!.create(Api::class.java)

        var call:retrofit2.Call<Artists> = service.getMyArtists("Bearer $token")

        call.enqueue(object : Callback<Artists> {
            override fun onResponse(call: retrofit2.Call<Artists>, response: Response<Artists>) {
                respArtists.value = response.body()!!
            }

            override fun onFailure(call: retrofit2.Call<Artists>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getMyFavTracks(token:String) {

        var service = RetrofitInstance().getRefrofitInstance()!!.create(Api::class.java)

        var call:retrofit2.Call<Tracks> = service.getMyTracks("Bearer $token")

        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: retrofit2.Call<Tracks>, response: Response<Tracks>) {
                respTracks.value = response.body()!!
            }

            override fun onFailure(call: retrofit2.Call<Tracks>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
}