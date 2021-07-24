package com.uhi5d.spotibud.presentation.ui.detailed.artist

import android.os.Parcelable
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsArtistsItem
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
