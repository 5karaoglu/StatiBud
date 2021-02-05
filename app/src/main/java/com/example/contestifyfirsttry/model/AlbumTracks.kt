package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class AlbumTracks (

    @SerializedName("href") val href : String,
    @SerializedName("items") val items : List<AlbumTrackItem>,
    @SerializedName("limit") val limit : Int,
    @SerializedName("next") val next : String,
    @SerializedName("offset") val offset : Int,
    @SerializedName("previous") val previous : String,
    @SerializedName("total") val total : Int
)
data class AlbumTrackItem (

    @SerializedName("artists") val artists : List<Artists>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("disc_number") val disc_number : Int,
    @SerializedName("duration_ms") val duration_ms : Int,
    @SerializedName("explicit") val explicit : Boolean,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("is_local") val is_local : Boolean,
    @SerializedName("name") val name : String,
    @SerializedName("preview_url") val preview_url : String,
    @SerializedName("track_number") val track_number : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)