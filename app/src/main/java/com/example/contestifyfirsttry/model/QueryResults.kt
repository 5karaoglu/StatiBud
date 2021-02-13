package com.example.contestifyfirsttry.model

import com.google.gson.annotations.SerializedName

data class QueryResults (

    @SerializedName("albums") val albums : QueryResultAlbums,
    @SerializedName("artists") val artists : QueryResultArtists,
    @SerializedName("tracks") val tracks : QueryResultTracks
)

data class QueryResultAlbums (

    @SerializedName("href") val href : String,
    @SerializedName("items") val items : List<QueryResultAlbumItem>,
    @SerializedName("limit") val limit : Int,
    @SerializedName("next") val next : String,
    @SerializedName("offset") val offset : Int,
    @SerializedName("previous") val previous : String,
    @SerializedName("total") val total : Int
)
data class QueryResultAlbumItem (

    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<QueryResultArtist>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("external_urls") val external_urls : QueryResultExternal_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<QueryResultImages>,
    @SerializedName("name") val name : String,
    @SerializedName("release_date") val release_date : String,
    @SerializedName("release_date_precision") val release_date_precision : String,
    @SerializedName("total_tracks") val total_tracks : Int,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
//this one dont have image. used in album and tracks
data class QueryResultArtist (
    @SerializedName("external_urls") val external_urls : QueryResultExternal_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class QueryResultArtists (

    @SerializedName("external_urls") val external_urls : QueryResultExternal_urls,
    @SerializedName("href") val href : String,
    @SerializedName("items") val items: List<QueryResultArtistsItem>,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class QueryResultArtistsItem (
    @SerializedName("external_urls") val external_urls : QueryResultExternal_urls,
    @SerializedName("followers") val followers: QueryResultFollowers,
    @SerializedName("genres") val genres: List<String>,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("images") val images : List<QueryResultImages>,
    @SerializedName("popularity")val popularity: Long,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class QueryResultTracks (

    @SerializedName("href") val href : String,
    @SerializedName("items") val items : List<QueryResultTrackItem>,
    @SerializedName("limit") val limit : Int,
    @SerializedName("next") val next : String,
    @SerializedName("offset") val offset : Int,
    @SerializedName("previous") val previous : String,
    @SerializedName("total") val total : Int
)
data class QueryResultTrackItem (

    @SerializedName("album") val album : Album,
    @SerializedName("artists") val artists : List<QueryResultArtist>,
    @SerializedName("available_markets") val available_markets : List<String>,
    @SerializedName("disc_number") val disc_number : Int,
    @SerializedName("duration_ms") val duration_ms : Int,
    @SerializedName("explicit") val explicit : Boolean,
    @SerializedName("external_ids") val external_ids : QueryResultExternal_ids,
    @SerializedName("external_urls") val external_urls : QueryResultExternal_urls,
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


data class QueryResultExternal_ids (

    @SerializedName("isrc") val isrc : String
)
data class QueryResultExternal_urls (

    @SerializedName("spotify") val spotify : String
)
data class QueryResultFollowers (

    @SerializedName("href") val href : String,
    @SerializedName("total") val total : Int
)
data class QueryResultImages (

    @SerializedName("height") val height : Int,
    @SerializedName("url") val url : String,
    @SerializedName("width") val width : Int
)