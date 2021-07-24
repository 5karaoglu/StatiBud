package com.uhi5d.spotibud.presentation.ui.detailed.album

import android.os.Parcelable
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsAlbumsItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedAlbumFragmentModel(
    val id: String,
    val name: String,
    val imageUri: String
): Parcelable

fun SearchResultsAlbumsItem.toDetailedAlbumFragmentModel() = DetailedAlbumFragmentModel(
    this.id!!, this.name!!, this.images!![0].url!!
)