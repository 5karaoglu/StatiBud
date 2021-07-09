package com.uhi5d.spotibud.domain.model.currentuser


import com.google.gson.annotations.SerializedName
import com.uhi5d.spotibud.domain.model.ExternalUrls
import com.uhi5d.spotibud.domain.model.Image

data class CurrentUser(
    val country: String?,
    @SerializedName("display_name")
    val displayName: String?,
    val email: String?,
    @SerializedName("explicit_content")
    val explicitContent: ExplicitContent?,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val followers: Followers?,
    val href: String?,
    val id: String?,
    val images: List<Image>?,
    val product: String?,
    val type: String?,
    val uri: String?
)