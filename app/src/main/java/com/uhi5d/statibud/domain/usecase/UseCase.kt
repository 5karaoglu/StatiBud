package com.uhi5d.statibud.domain.usecase

import com.uhi5d.statibud.domain.model.MyArtists
import com.uhi5d.statibud.domain.model.accesstoken.AccessToken
import com.uhi5d.statibud.domain.model.album.Album
import com.uhi5d.statibud.domain.model.albumstracks.AlbumsTracksResponse
import com.uhi5d.statibud.domain.model.artist.Artist
import com.uhi5d.statibud.domain.model.artistalbums.ArtistAlbums
import com.uhi5d.statibud.domain.model.artists.Artists
import com.uhi5d.statibud.domain.model.artisttoptracks.ArtistTopTracks
import com.uhi5d.statibud.domain.model.currentuser.CurrentUser
import com.uhi5d.statibud.domain.model.devices.Devices
import com.uhi5d.statibud.domain.model.genres.Genres
import com.uhi5d.statibud.domain.model.mytracks.MyTracks
import com.uhi5d.statibud.domain.model.recenttracks.RecentTracks
import com.uhi5d.statibud.domain.model.recommendations.Recommendations
import com.uhi5d.statibud.domain.model.relatedartists.RelatedArtists
import com.uhi5d.statibud.domain.model.searchresults.SearchResults
import com.uhi5d.statibud.domain.model.track.Track
import com.uhi5d.statibud.domain.model.trackaudiofeatures.TrackAudioFeatures
import com.uhi5d.statibud.util.DataState
import kotlinx.coroutines.flow.Flow

interface UseCase {


    suspend fun getMyTopArtists(
        token: String,
        timeRange: String,
        limit: Int? = null
    ): Flow<DataState<MyArtists>>

    suspend fun getMyTopTracks(
        token: String,
        timeRange: String,
        limit: Int? = null
    ): Flow<DataState<MyTracks>>

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
        market: String? = null
    ): Flow<DataState<Recommendations>>

    suspend fun getMyProfile(
        token: String
    ): Flow<DataState<CurrentUser>>

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
    ): Flow<DataState<SearchResults>>

    suspend fun getAvailableDevices(
        token: String
    ): Flow<DataState<Devices>>

    suspend fun getGenres(
        token: String
    ): Flow<DataState<Genres>>

    suspend fun saveGenres(
        genres: Genres
    )

    fun getAlbumsTracks(
        token: String,
        albumId: String
    ): Flow<DataState<AlbumsTracksResponse>>

    fun getToken(
        url:String,
    clientId:String,grantType:String,
    code:String,redirectUri:String,codeVerifier:String
    ): Flow<DataState<AccessToken>>
}