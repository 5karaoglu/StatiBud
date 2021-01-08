package com.example.contestifyfirsttry

import com.example.contestifyfirsttry.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("me/top/artists?limit=50")
    fun getMyArtists(@Header("Authorization") token:String):Call<Artists>

    @GET("me/top/tracks?limit=50")
    fun getMyTracks(@Header("Authorization") token:String):Call<Tracks>

    @GET("me")
    fun getMyProfile(@Header("Authorization") token:String):Call<User>
}