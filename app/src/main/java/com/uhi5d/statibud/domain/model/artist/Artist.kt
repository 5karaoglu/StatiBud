package com.uhi5d.statibud.domain.model.artist


import com.google.gson.annotations.SerializedName
import com.uhi5d.statibud.domain.model.ExternalUrls
import com.uhi5d.statibud.domain.model.Image

data class Artist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls?,
    val followers: Followers?,
    val genres: List<String>?,
    val href: String?,
    val id: String?,
    val images: List<Image>?,
    val name: String?,
    val popularity: Int?,
    val type: String?,
    val uri: String?
)