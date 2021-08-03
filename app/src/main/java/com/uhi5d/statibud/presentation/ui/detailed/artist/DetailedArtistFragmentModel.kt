package com.uhi5d.statibud.presentation.ui.detailed.artist

import android.os.Parcelable
import com.uhi5d.statibud.domain.model.MyArtistsItem
import com.uhi5d.statibud.domain.model.artist.Artist
import com.uhi5d.statibud.domain.model.relatedartists.RelatedArtistsArtist
import com.uhi5d.statibud.domain.model.searchresults.SearchResultsArtistsItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedArtistFragmentModel(
    val id: String,
    val name: String,
    val imageUri: String
): Parcelable

fun SearchResultsArtistsItem.toDetailedArtistFragmentModel() =
    DetailedArtistFragmentModel(this.id.toString(), this.name.toString(),
        this.images!![0].url.toString()
    )

fun Artist.toDetailedArtistFragmentModel() = DetailedArtistFragmentModel(
    this.id.toString(), this.name.toString(),
    this.images!![0].url.toString()
)
fun RelatedArtistsArtist.toDetailedArtistFragmentModel() = DetailedArtistFragmentModel(
    this.id.toString(), this.name.toString(),
    this.images!![0].url.toString()
)

fun MyArtistsItem.toDetailedArtistFragmentModel() = DetailedArtistFragmentModel(
    this.id.toString(), this.name.toString(),
    this.images!![0].url.toString()
)

