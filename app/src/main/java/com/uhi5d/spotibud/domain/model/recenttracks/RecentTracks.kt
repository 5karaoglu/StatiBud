package com.uhi5d.spotibud.domain.model.recenttracks


import com.google.gson.annotations.SerializedName
import com.uhi5d.spotibud.domain.model.ExternalIds
import com.uhi5d.spotibud.domain.model.ExternalUrls
import com.uhi5d.spotibud.domain.model.Image

data class RecentTracks(
    val cursors: Cursors?,
    val href: String?,
    val items: List<RecentTracksItem>?,
    val limit: Int?,
    val next: String?
)
data class RecentTracksItem(

    val context: String?,
    @SerializedName("played_at")
    val playedAt: String?,
    val track: RecentTracksTrack?
)

data class RecentTracksTrack(
    val album: RecentTracksAlbum?,
    val artists: List<RecentTracksArtist>?,
    @SerializedName("available_markets")
    val availableMarkets: List<String>?,
    @SerializedName("disc_number")
    val discNumber: Int?,
    @SerializedName("duration_ms")
    val durationMs: Int?,
    val explicit: Boolean?,
    @SerializedName("external_ids")
    val externalIds: ExternalIds?,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    @SerializedName("is_local")
    val isLocal: Boolean?,
    val name: String?,
    val popularity: Int?,
    @SerializedName("preview_url")
    val previewUrl: String?,
    @SerializedName("track_number")
    val trackNumber: Int?,
    val type: String?,
    val uri: String?
)

data class RecentTracksArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val name: String?,
    val type: String?,
    val uri: String?
)

data class RecentTracksAlbum(
    @SerializedName("album_type")
    val albumType: String?,
    val artists: List<RecentTracksArtist>?,
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

data class Cursors(
    val after: String?,
    val before: String?
)
