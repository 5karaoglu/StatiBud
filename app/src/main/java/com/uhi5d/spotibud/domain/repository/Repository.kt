package com.uhi5d.spotibud.domain.repository

import com.uhi5d.spotibud.domain.model.MyArtists
import com.uhi5d.spotibud.domain.model.Recommendations
import com.uhi5d.spotibud.domain.model.album.Album
import com.uhi5d.spotibud.domain.model.artist.Artist
import com.uhi5d.spotibud.domain.model.artistalbums.ArtistAlbums
import com.uhi5d.spotibud.domain.model.artists.Artists
import com.uhi5d.spotibud.domain.model.artisttoptracks.ArtistTopTracks
import com.uhi5d.spotibud.domain.model.currentuser.CurrentUser
import com.uhi5d.spotibud.domain.model.devices.Devices
import com.uhi5d.spotibud.domain.model.genres.Genres
import com.uhi5d.spotibud.domain.model.mytracks.MyTracks
import com.uhi5d.spotibud.domain.model.recenttracks.RecentTracks
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtists
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.model.track.Track
import com.uhi5d.spotibud.domain.model.trackaudiofeatures.TrackAudioFeatures
import com.uhi5d.spotibud.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getMyTopArtists(
        token:String,
        timeRange: String,
        limit: Int? = null): Flow<DataState<MyArtists>>

    suspend fun getMyTopTracks(
        token:String,
        timeRange: String,
        limit: Int? = null): Flow<DataState<MyTracks>>

    suspend fun getRecommendations(
        token: String,
        seedArtists: String,
        seedTracks: String,
        market: String? = "US"
    ): Flow<DataState<Recommendations>>

    suspend fun getRecommendations(
        token: String,
        seedTracks: String,
        seedGenre: String,
        targetAcousticness: String,
        targetDanceability: String,
        targetEnergy: String,
        targetInstrumentalness: String,
        targetLiveness: String,
        targetValence: String,
        market:String? = null
    ): Flow<DataState<Recommendations>>

    suspend fun getMyProfile(
        token: String
    ):Flow<DataState<CurrentUser>>

    suspend fun getMyRecentPlayed(
        token: String,
        limit: Int? = null
    ): Flow<DataState<RecentTracks>>

    suspend fun getArtist(
        token: String,
        artistId: String
    ): Flow<DataState<Artist>>

    suspend fun getSeveralArtists(
        token: String,
        ids: String
    ): Flow<DataState<Artists>>

    suspend fun getArtistAlbums(
        token: String,
        artistId: String,
        market: String? = null
    ): Flow<DataState<ArtistAlbums>>

    suspend fun getArtistRelatedArtists(
        token: String,
        artistId: String
    ): Flow<DataState<RelatedArtists>>

    suspend fun getArtistTopTracks(
        token: String,
        artistId: String,
        market: String? = null
    ): Flow<DataState<ArtistTopTracks>>

    suspend fun getTrackAudioFeature(
        token: String,
        trackId: String
    ): Flow<DataState<TrackAudioFeatures>>

    suspend fun getTrack(
        token: String,
        trackId: String
    ): Flow<DataState<Track>>

    suspend fun getAlbum(
        token: String,
        albumId: String
    ): Flow<DataState<Album>>

    suspend fun search(
        token: String,
        query: String,
        type: String? = null,
        limit: Int? = null
    ):Flow<DataState<SearchResults>>

    suspend fun getAvailableDevices(
        token: String
    ):Flow<DataState<Devices>>

    suspend fun getGenres(
        token: String
    ):Genres

    suspend fun saveGenres(
        genres: Genres
    )

}