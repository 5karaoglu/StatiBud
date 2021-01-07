package com.example.contestifyfirsttry

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("me/top/artists")
    fun getMyArtists(@Header("Authorization") token:String):Call<Artists>

    @GET("me/top/tracks")
    fun getMyTracks(@Header("Authorization") token:String):Call<Tracks>
}