package com.example.contestifyfirsttry

import com.example.contestifyfirsttry.model.External_urls
import com.example.contestifyfirsttry.model.Image
import com.example.contestifyfirsttry.model.Item
import com.google.gson.annotations.SerializedName


data class Artists (
    @SerializedName("items") val items : List<Item>,
    @SerializedName("total") val total : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("offset") val offset : Int,
    @SerializedName("previous") val previous : String,
    @SerializedName("href") val href : String,
    @SerializedName("next") val next : String
)



data class Followers (
    @SerializedName("href") val href : String,
    @SerializedName("total") val total : Int
)


enum class Type {
    Artist
}

