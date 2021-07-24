package com.uhi5d.spotibud.presentation.ui.detailed.track

import android.os.Parcelable
import com.uhi5d.spotibud.domain.model.recenttracks.RecentTracksItem
import com.uhi5d.spotibud.domain.model.recommendations.RecommendationsTrack
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
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