package com.uhi5d.spotibud.model

import com.google.gson.annotations.SerializedName

data class TrackAudioFeatures (

    @SerializedName("danceability") val danceability : Double,
    @SerializedName("energy") val energy : Double,
    @SerializedName("key") val key : Int,
    @SerializedName("loudness") val loudness : Double,
    @SerializedName("mode") val mode : Int,
    @SerializedName("speechiness") val speechiness : Double,
    @SerializedName("acousticness") val acousticness : Double,
    @SerializedName("instrumentalness") val instrumentalness : Double,
    @SerializedName("liveness") val liveness : Double,
    @SerializedName("valence") val valence : Double,
    @SerializedName("tempo") val tempo : Double,
    @SerializedName("type") val type : String,
    @SerializedName("id") val id : String,
    @SerializedName("uri") val uri : String,
    @SerializedName("track_href") val track_href : String,
    @SerializedName("analysis_url") val analysis_url : String,
    @SerializedName("duration_ms") val duration_ms : Int,
    @SerializedName("time_signature") val time_signature : Int
)