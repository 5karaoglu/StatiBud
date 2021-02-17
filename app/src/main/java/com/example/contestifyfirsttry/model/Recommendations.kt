package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class Recommendations (
    val tracks: List<RecommendationTrack>,
    val seeds: List<Seed>
)

data class Seed (
    @SerializedName("initialPoolSize") val initialPoolSize : Int,
    @SerializedName("afterFilteringSize") val afterFilteringSize : Int,
    @SerializedName("afterRelinkingSize") val afterRelinkingSize : Int,
    @SerializedName("id") val id : String,
    @SerializedName("type") val type : String,
    @SerializedName("href") val href : String? = null

)

data class RecommendationTrack (
    @SerializedName("album") val album : RecommendationAlbum,
    @SerializedName("artists") val artists : List<Artists>,
    @SerializedName("disc_number") val disc_number : Int,
    @SerializedName("duration_ms") val duration_ms : Int,
    @SerializedName("explicit") val explicit : Boolean,
    @SerializedName("external_ids") val external_ids : ExternalIDS,
    @SerializedName("external_urls") val external_urls : ExternalUrls,
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

data class RecommendationAlbum (
    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<LinkedFrom>,
    @SerializedName("external_urls") val external_urls : ExternalUrls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<RecommendationImage>,
    @SerializedName("name") val name : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("release_date_precision") val release_date_precision : String,
    @SerializedName("total_tracks") val total_tracks : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String

)
data class LinkedFrom (

    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class ExternalUrls (
    @SerializedName("spotify") val spotify : String
)
data class RecommendationImage (
    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int
)
data class ExternalIDS (
    @SerializedName("isrc") val isrc : String
)

