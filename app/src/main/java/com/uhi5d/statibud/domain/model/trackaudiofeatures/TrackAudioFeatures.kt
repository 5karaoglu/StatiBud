package com.uhi5d.statibud.domain.model.trackaudiofeatures


import com.google.gson.annotations.SerializedName

data class TrackAudioFeatures(
    val acousticness: Double?,
    @SerializedName("analysis_url")
    val analysisUrl: String?,
    val danceability: Double?,
    @SerializedName("duration_ms")
    val durationMs: Int?,
    val energy: Double?,
    val id: String?,
    val instrumentalness: Double?,
    val key: Int?,
    val liveness: Double?,
    val loudness: Double?,
    val mode: Int?,
    val speechiness: Double?,
    val tempo: Double?,
    @SerializedName("time_signature")
    val timeSignature: Int?,
    @SerializedName("track_href")
    val trackHref: String?,
    val type: String?,
    val uri: String?,
    val valence: Double?
)