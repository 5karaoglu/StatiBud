package com.uhi5d.statibud.presentation.ui.detailed.track

import android.os.Parcelable
import com.uhi5d.statibud.domain.model.albumstracks.AlbumsTracksItem
import com.uhi5d.statibud.domain.model.artisttoptracks.ArtistTopTracksTrack
import com.uhi5d.statibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.statibud.domain.model.recenttracks.RecentTracksItem
import com.uhi5d.statibud.domain.model.recommendations.RecommendationsTrack
import com.uhi5d.statibud.domain.model.searchresults.SearchResultsTracksItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailedTrackFragmentModel(
    val id: String,
    val name: String,
    val imageUri: String
):Parcelable

fun RecentTracksItem.toDetailedTrackFragmentModel() = DetailedTrackFragmentModel(
    this.track?.id!!, this.track.name!!, this.track.album?.images?.get(0)?.url!!
)
fun RecommendationsTrack.toDetailedTrackFragmentModel() = DetailedTrackFragmentModel(
    this.id!!, this.name!!, this.album?.images?.get(0)?.url!!
)
fun SearchResultsTracksItem.toDetailedTrackFragmentModel() = DetailedTrackFragmentModel(
    this.id!!, this.name!!, this.album?.images?.get(0)?.url!!
)
fun ArtistTopTracksTrack.toDetailedTrackFragmentModel() = DetailedTrackFragmentModel(
    this.id!!, this.name!!, this.album?.images?.get(0)?.url!!
)
fun MyTracksItem.toDetailedTrackFragmentModel() = DetailedTrackFragmentModel(
    this.id!!, this.name!!, this.album?.images?.get(0)?.url!!
)
fun AlbumsTracksItem.toDetailedTrackFragmentModel() = DetailedTrackFragmentModel(
    this.id!!, this.name!!, ""
)