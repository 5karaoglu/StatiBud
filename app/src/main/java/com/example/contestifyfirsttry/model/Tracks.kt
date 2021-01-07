package com.example.contestifyfirsttry

import com.google.gson.annotations.SerializedName

data class Tracks (
    @SerializedName("items") val items : List<TrackItems>,
    @SerializedName("total") val total : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("offset") val offset : Int,
    @SerializedName("href") val href : String,
    @SerializedName("previous") val previous : String,
    @SerializedName("next") val next : String
)

data class TrackItems (
    @SerializedName("album") val album : Album,
    @SerializedName("artists") val artists : List<TrackArtists>,
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

data class Album (
    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<Artists>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<TrackImages>,
    @SerializedName("name") val name : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("release_date_precision") val release_date_precision : String,
    @SerializedName("total_tracks") val total_tracks : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)

enum class AlbumType {
    Album,
    Single
}

data class TrackArtists (
    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)

data class External_urls (
    @SerializedName("spotify") val spotify : String
)

enum class ArtistType {
    Artist
}

data class TrackImages (
    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int
)

enum class ReleaseDatePrecision {
    Day
}

enum class AlbumTypeEnum {
    Album
}

data class External_ids (
    @SerializedName("isrc") val isrc : String
)

enum class ItemType {
    Track
}
