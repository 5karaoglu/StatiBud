package com.uhi5d.spotibud.model

import com.google.gson.annotations.SerializedName

data class Devices(
    @SerializedName("devices") val devices : List<Device>
)
data class Device (
    @SerializedName("id") val id : String,
    @SerializedName("is_active") val is_active : Boolean,
    @SerializedName("is_private_session") val is_private_session : Boolean,
    @SerializedName("is_restricted") val is_restricted : Boolean,
    @SerializedName("name") val name : String,
    @SerializedName("type") val type : String,
    @SerializedName("volume_percent") val volume_percent : Int
)