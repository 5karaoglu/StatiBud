package com.example.contestifyfirsttry

import com.example.contestifyfirsttry.model.External_urls
import com.example.contestifyfirsttry.model.Image
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

data class Item (
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("followers") val followers : Followers,
    @SerializedName("genres") val genres : List<String>,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<Image>,
    @SerializedName("name") val name : String,
    @SerializedName("popularity") val popularity : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)


data class Followers (
    @SerializedName("href") val href : String,
    @SerializedName("total") val total : Int
)


enum class Type {
    Artist
}

