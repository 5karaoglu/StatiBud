package com.uhi5d.statibud.domain.model.devices


import com.google.gson.annotations.SerializedName

data class Device(
    val id: String?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("is_private_session")
    val isPrivateSession: Boolean?,
    @SerializedName("is_restricted")
    val isRestricted: Boolean?,
    val name: String?,
    val type: String?,
    @SerializedName("volume_percent")
    val volumePercent: Int?
)