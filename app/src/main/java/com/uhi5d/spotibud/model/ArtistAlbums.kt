package com.uhi5d.spotibud.model

import com.google.gson.annotations.SerializedName

data class ArtistAlbums (

    @SerializedName("href") val href : String,
    @SerializedName("items") val items : List<ArtistAlbumsItems>,
    @SerializedName("limit") val limit : Int,
    @SerializedName("next") val next : String,
    @SerializedName("offset") val offset : Int,
    @SerializedName("previous") val previous : String,
    @SerializedName("total") val total : Int
)

data class ArtistAlbumsArtists (

    @SerializedName("external_urls") val external_urls : External_urls,
    @SerializedName("href") val href : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("uri") val uri : String
)
data class ArtistAlbumsItems (

    @SerializedName("album_group") val album_group : String,
    @SerializedName("album_type") val album_type : String,
    @SerializedName("artists") val artists : List<ArtistAlbumsArtists>,
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