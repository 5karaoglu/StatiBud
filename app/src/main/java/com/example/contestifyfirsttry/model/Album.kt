package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class Album (

    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<AlbumArtists>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("copyrights") val copyrights : List<Copyrights>,
    @SerializedName("external_ids") val external_ids : AlbumExternal_ids,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("genres") val genres : List<String>,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<Image>,
    @SerializedName("label") val label : String,
    @SerializedName("name") val name : String,
    @SerializedName("popularity") val popularity : Int,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("release_date_precision") val release_date_precision : String,
    @SerializedName("total_tracks") val total_tracks : Int,
    @SerializedName("tracks") val tracks : AlbumTracksAlbum,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class AlbumArtists (

    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class Copyrights (

    @SerializedName("text") val text : String,
    @SerializedName("type") val type : String
)
data class AlbumExternal_ids (

    @SerializedName("upc") val upc : String
)
data class AlbumItems (

    @SerializedName("artists") val artists : List<AlbumArtists>,
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
data class AlbumTracksAlbum (

    @SerializedName("href") val href : String,
    @SerializedName("items") val items : List<AlbumItems>,
    @SerializedName("limit") val limit : Int,
    @SerializedName("next") val next : String,
    @SerializedName("offset") val offset : Int,
    @SerializedName("previous") val previous : String,
    @SerializedName("total") val total : Int
)
