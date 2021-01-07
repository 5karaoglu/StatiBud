package com.example.contestifyfirsttry

import com.google.gson.annotations.SerializedName

data class Tracks (
    @SerializedName("items")val items: List<TrackItem>,
    @SerializedName("total")val total: Long,
    @SerializedName("limit")val limit: Long,
    @SerializedName("offset")val offset: Long,
    @SerializedName("href")val href: String,
    @SerializedName("previous")val previous: Any? = null,
    @SerializedName("next")val next: Any? = null
)

data class TrackItem (
    @SerializedName("items")val album: Album,
    @SerializedName("artists")val artists: List<Artist>,
    @SerializedName("available_markets")val availableMarkets: List<String>,
    @SerializedName("disc_number")val discNumber: Long,
    @SerializedName("duration_ms")val durationMS: Long,
    @SerializedName("explicit")val explicit: Boolean,
    @SerializedName("external_ids")val externalIDS: ExternalIDS,
    @SerializedName("external_urls")val externalUrls: TrackExternalUrls,
    @SerializedName("href")val href: String,
    @SerializedName("id")val id: String,
    @SerializedName("is_local")val isLocal: Boolean,
    @SerializedName("name")val name: String,
    @SerializedName("popularity")val popularity: Long,
    @SerializedName("preview_url")val previewURL: String,
    @SerializedName("track_number")val trackNumber: Long,
    @SerializedName("type")val type: ItemType,
    @SerializedName("uri")val uri: String
)

data class Album (
    @SerializedName("album_type")val albumType: AlbumType,
    @SerializedName("artists")val artists: List<Artist>,
    @SerializedName("available_markets")val availableMarkets: List<String>,
    @SerializedName("external_urls")val externalUrls: TrackExternalUrls,
    @SerializedName("href")val href: String,
    @SerializedName("id")val id: String,
    @SerializedName("images")val images: List<TrackImage>,
    @SerializedName("name")val name: String,
    @SerializedName("release_date")val releaseDate: String,
    @SerializedName("release_date_precision")val releaseDatePrecision: ReleaseDatePrecision,
    @SerializedName("total_tracks")val totalTracks: Long,
    @SerializedName("type")val type: AlbumTypeEnum,
    @SerializedName("uri")val uri: String
)

enum class AlbumType {
    Album,
    Single
}

data class Artist (
    @SerializedName("external_urls")val externalUrls: TrackExternalUrls,
    @SerializedName("href")val href: String,
    @SerializedName("id")val id: String,
    @SerializedName("name")val name: String,
    @SerializedName("type")val type: ArtistType,
    @SerializedName("uri")val uri: String
)

data class TrackExternalUrls (
    @SerializedName("spotify")val spotify: String
)

enum class ArtistType {
    Artist
}

data class TrackImage (
    @SerializedName("height")val height: Long,
    @SerializedName("url")val url: String,
    @SerializedName("width")val width: Long
)

enum class ReleaseDatePrecision {
    Day
}

enum class AlbumTypeEnum {
    Album
}

data class ExternalIDS (
    @SerializedName("isrc")val isrc: String
)

enum class ItemType {
    Track
}
