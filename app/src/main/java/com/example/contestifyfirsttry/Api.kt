package com.example.contestifyfirsttry

import com.example.contestifyfirsttry.model.RecentTracks
import com.example.contestifyfirsttry.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("me/top/artists?limit=50")
    fun getMyArtists(@Header("Authorization") token:String, @Query("time_range") timeRange:String):Call<Artists>

    @GET("me/top/tracks?limit=50")
    fun getMyTracks(@Header("Authorization") token:String, @Query("time_range") timeRange:String):Call<Tracks>

    @GET("me")
    fun getMyProfile(@Header("Authorization") token:String):Call<User>

    @GET("me/player/recently-played?limit=20")
    fun getUserRecentPlayed(@Header("Authorization") token:String):Call<RecentTracks>
}