package com.uhi5d.spotibud.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    var retrofit : Retrofit? = null
    private val BASE_URL = "https://api.spotify.com/v1/"

    fun getRefrofitInstance(): Retrofit? {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}