package com.uhi5d.statibud.domain.model.recommendations


import com.google.gson.annotations.SerializedName
import com.uhi5d.statibud.domain.model.ExternalIds
import com.uhi5d.statibud.domain.model.ExternalUrls
import com.uhi5d.statibud.domain.model.Image

data class Recommendations(
    val seeds: List<RecommendationsSeed>?,
    val tracks: List<RecommendationsTrack>?
)

data class RecommendationsArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val name: String?,
    val type: String?,
    val uri: String?
)

data class RecommendationsAlbum(
    @SerializedName("album_type")
    val albumType: String?,
    val artists: List<RecommendationsArtist>?,
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

data class RecommendationsLinkedFrom(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val type: String?,
    val uri: String?
)

data class RecommendationsSeed(
    val afterFilteringSize: Int?,
    val afterRelinkingSize: Int?,
    val href: String?,
    val id: String?,
    val initialPoolSize: Int?,
    val type: String?
)
data class RecommendationsTrack(
    val album: RecommendationsAlbum?,
    val artists: List<RecommendationsArtist>?,
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
    @SerializedName("is_playable")
    val isPlayable: Boolean?,
    @SerializedName("linked_from")
    val linkedFrom: RecommendationsLinkedFrom?,
    val name: String?,
    val popularity: Int?,
    @SerializedName("preview_url")
    val previewUrl: Any?,
    @SerializedName("track_number")
    val trackNumber: Int?,
    val type: String?,
    val uri: String?
)