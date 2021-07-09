package com.uhi5d.spotibud.domain.model.album


import com.google.gson.annotations.SerializedName
import com.uhi5d.spotibud.domain.model.ExternalIds
import com.uhi5d.spotibud.domain.model.ExternalUrls
import com.uhi5d.spotibud.domain.model.Image

data class Album(
    @SerializedName("album_type")
    val albumType: String?,
    val artists: List<AlbumArtist>?,
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    val copyrights: List<Copyright>?,
    @SerializedName("external_ids")
    val externalIds: ExternalIds?,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val genres: List<Any>?,
    val href: String?,
    val id: String?,
    val images: List<Image>?,
    val label: String?,
    val name: String?,
    val popularity: Int?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String?,
    @SerializedName("total_tracks")
    val totalTracks: Int?,
    val tracks: AlbumTracks?,
    val type: String?,
    val uri: String?
)

data class AlbumArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val name: String?,
    val type: String?,
    val uri: String?
)

data class AlbumTracks(
    val href: String?,
    val items: List<AlbumTracksItem>?,
    val limit: Int?,
    val next: Any?,
    val offset: Int?,
    val previous: Any?,
    val total: Int?
)

data class AlbumTracksItem(
    val artists: List<AlbumArtist>?,
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    @SerializedName("disc_number")
    val discNumber: Int?,
    @SerializedName("duration_ms")
    val durationMs: Int?,
    val explicit: Boolean?,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    @SerializedName("is_local")
    val isLocal: Boolean?,
    val name: String?,
    @SerializedName("preview_url")
    val previewUrl: String?,
    @SerializedName("track_number")
    val trackNumber: Int?,
    val type: String?,
    val uri: String?
)

data class Copyright(
    val text: String?,
    val type: String?
)