package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class ArtistTopTracks(
    @SerializedName("tracks") val tracks : List<TracksTopTrack>
)
data class TracksTopTrack (

    @SerializedName("album") val album : TopTrackAlbum,
    @SerializedName("artists") val artists : List<TopTrackArtists>,
    @SerializedName("disc_number") val disc_number : Int,
    @SerializedName("duration_ms") val duration_ms : Int,
    @SerializedName("explicit") val explicit : Boolean,
    @SerializedName("external_ids") val external_ids : External_ids,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("is_local") val is_local : Boolean,
    @SerializedName("is_playable") val is_playable : Boolean,
    @SerializedName("name") val name : String,
    @SerializedName("popularity") val popularity : Int,
    @SerializedName("preview_url") val preview_url : String,
    @SerializedName("track_number") val track_number : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class TopTrackAlbum (

    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<TopTrackArtists>,
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
data class TopTrackArtists (

    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)




