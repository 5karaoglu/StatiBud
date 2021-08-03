package com.uhi5d.statibud.presentation.ui.detailed.album

import android.os.Parcelable
import com.uhi5d.statibud.domain.model.artistalbums.ArtistAlbumsItem
import com.uhi5d.statibud.domain.model.searchresults.SearchResultsAlbumsItem
import com.uhi5d.statibud.domain.model.track.Track
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

fun ArtistAlbumsItem.toDetailedAlbumFragmentModel() = DetailedAlbumFragmentModel(
    this.id!!, this.name!!, this.images!![0].url!!
)
fun Track.toDetailedAlbumFragmentModel() = DetailedAlbumFragmentModel(
    this.album?.id!!, this.name!!, this.album.images!![0].url!!
)
