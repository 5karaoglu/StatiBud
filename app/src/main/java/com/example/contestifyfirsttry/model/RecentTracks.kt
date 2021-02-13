package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class RecentTracks(
    @SerializedName("items") val items : List<Items>,
    @SerializedName("next") val next : String,
    @SerializedName("cursors") val cursors : Cursors,
    @SerializedName("limit") val limit : Int,
    @SerializedName("href") val href : String
)
data class RecentTrackAlbum (

    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<RecentTrackArtists>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<Image>,
    @SerializedName("name") val name : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("release_date_precision") val release_date_precision : String,
    @SerializedName("total_tracks") val total_tracks : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class RecentTrackArtists (

    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class Cursors (

    @SerializedName("after") val after : String,
    @SerializedName("before") val before : String
)

data class Items (

    @SerializedName("track") val track : Track,
    @SerializedName("played_at") val played_at : String,
    @SerializedName("context") val context : String
)
data class Track (

    @SerializedName("album") val album : RecentTrackAlbum,
    @SerializedName("artists") val artists : List<RecentTrackArtists>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("disc_number") val disc_number : Int,
    @SerializedName("duration_ms") val duration_ms : Int,
    @SerializedName("explicit") val explicit : Boolean,
    @SerializedName("external_ids") val external_ids : External_ids,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("is_local") val is_local : Boolean,
    @SerializedName("name") val name : String,
    @SerializedName("popularity") val popularity : Int,
    @SerializedName("preview_url") val preview_url : String,
    @SerializedName("track_number") val track_number : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
