package com.example.contestifyfirsttry

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
    @SerializedName("external_urls") val external_urls : ExternalUrls,
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

data class ExternalUrls (
    @SerializedName("spotify") val spotify : String
)

data class Followers (
    @SerializedName("href") val href : String,
    @SerializedName("total") val total : Int
)

data class Image (
    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int
)

enum class Type {
    Artist
}

