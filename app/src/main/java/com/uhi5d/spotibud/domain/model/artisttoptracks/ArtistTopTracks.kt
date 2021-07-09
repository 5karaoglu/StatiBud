package com.uhi5d.spotibud.domain.model.artisttoptracks


import com.google.gson.annotations.SerializedName
import com.uhi5d.spotibud.domain.model.ExternalIds
import com.uhi5d.spotibud.domain.model.ExternalUrls
import com.uhi5d.spotibud.domain.model.Image

data class ArtistTopTracks(
    val tracks: List<ArtistTopTracksTrack>?
)

data class ArtistTopTracksTrack(
    val album: ArtistTopTracksAlbum?,
    val artists: List<ArtistTopTracksArtist>?,
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
    val name: String?,
    val popularity: Int?,
    @SerializedName("preview_url")
    val previewUrl: String?,
    @SerializedName("track_number")
    val trackNumber: Int?,
    val type: String?,
    val uri: String?
)

data class ArtistTopTracksArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val name: String?,
    val type: String?,
    val uri: String?
)

data class ArtistTopTracksAlbum(
    @SerializedName("album_type")
    val albumType: String?,
    val artists: List<ArtistTopTracksArtist>?,
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