package com.uhi5d.spotibud.domain.model


import com.google.gson.annotations.SerializedName

data class MyArtists(
    val href: String?,
    val items: List<MyArtistsItem>?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: Any?,
    val total: Int?
)

data class MyArtistsItem(
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
