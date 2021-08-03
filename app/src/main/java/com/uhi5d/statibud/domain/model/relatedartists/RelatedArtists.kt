package com.uhi5d.statibud.domain.model.relatedartists


import com.google.gson.annotations.SerializedName
import com.uhi5d.statibud.domain.model.ExternalUrls
import com.uhi5d.statibud.domain.model.Image
import com.uhi5d.statibud.domain.model.artist.Followers

data class RelatedArtists(
    val artists: List<RelatedArtistsArtist>?
)

data class RelatedArtistsArtist(
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