package com.uhi5d.spotibud.domain.model.albumstracks


import com.google.gson.annotations.SerializedName
import com.uhi5d.spotibud.domain.model.ExternalUrls

data class AlbumsTracksResponse(
    val href: String? = null,
    val items: List<AlbumsTracksItem>? = null,
    val limit: Int? = null,
    val next: Any? = null,
    val offset: Int? = null,
    val previous: Any? = null,
    val total: Int? = null
)
data class AlbumsTracksItem(
    val artists: List<Artist>? = null,
    @SerializedName("available_markets")
    val availableMarkets: List<String>? = null,
    @SerializedName("disc_number")
    val discNumber: Int? = null,
    @SerializedName("duration_ms")
    val durationMs: Int? = null,
    val explicit: Boolean? = null,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    @SerializedName("is_local")
    val isLocal: Boolean? = null,
    val name: String? = null,
    @SerializedName("preview_url")
    val previewUrl: String? = null,
    @SerializedName("track_number")
    val trackNumber: Int? = null,
    val type: String? = null,
    val uri: String? = null
)
data class Artist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls? = null,
    val href: String? = null,
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val uri: String? = null
)