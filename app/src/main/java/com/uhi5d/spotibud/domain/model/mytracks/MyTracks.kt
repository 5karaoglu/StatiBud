package com.uhi5d.spotibud.domain.model


import com.google.gson.annotations.SerializedName

data class MyTracks(
    val href: String?,
    val items: List<MyTrackItem>?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: Any?,
    val total: Int?
)

data class MyTrackItem(
    val album: MyTracksAlbum?,
    val artists: List<MyTracksArtist>?,
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

data class MyTracksAlbum(
    @SerializedName("album_type")
    val albumType: String?,
    val artists: List<MyTracksArtist>?,
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

data class MyTracksArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String?,
    val id: String?,
    val name: String?,
    val type: String?,
    val uri: String?
)

fun MyTracks.getTrackIds(): String {
    var str = ""
    this.items!!.forEach {
        str = str + it.id + ","
    }
    return str
}