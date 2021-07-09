package com.uhi5d.spotibud.domain.model.artistalbums


import com.google.gson.annotations.SerializedName
import com.uhi5d.spotibud.domain.model.ExternalUrls
import com.uhi5d.spotibud.domain.model.Image

data class ArtistAlbums(
    val href: String?,
    val items: List<ArtistAlbumsItem>?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: Any?,
    val total: Int?
)

data class ArtistAlbumsItem(
    @SerializedName("album_group")
    val albumGroup: String?,
    @SerializedName("album_type")
    val albumType: String?,
    val artists: List<ArtistAlbumsArtist>?,
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val images: List<Image>?,
    val name: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String?,
    @SerializedName("total_tracks")
    val totalTracks: Int?,
    val type: String?,
    val uri: String?
)

data class ArtistAlbumsArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val name: String?,
    val type: String?,
    val uri: String?
)