package com.uhi5d.spotibud.domain.usecase

import com.uhi5d.spotibud.data.repository.RepositoryImpl
import com.uhi5d.spotibud.domain.model.MyArtists
import com.uhi5d.spotibud.domain.model.accesstoken.AccessToken
import com.uhi5d.spotibud.domain.model.album.Album
import com.uhi5d.spotibud.domain.model.albumstracks.AlbumsTracksResponse
import com.uhi5d.spotibud.domain.model.artist.Artist
import com.uhi5d.spotibud.domain.model.artistalbums.ArtistAlbums
import com.uhi5d.spotibud.domain.model.artists.Artists
import com.uhi5d.spotibud.domain.model.artisttoptracks.ArtistTopTracks
import com.uhi5d.spotibud.domain.model.currentuser.CurrentUser
import com.uhi5d.spotibud.domain.model.devices.Devices
import com.uhi5d.spotibud.domain.model.genres.Genres
import com.uhi5d.spotibud.domain.model.mytracks.MyTracks
import com.uhi5d.spotibud.domain.model.recenttracks.RecentTracks
import com.uhi5d.spotibud.domain.model.recommendations.Recommendations
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtists
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.model.track.Track
import com.uhi5d.spotibud.domain.model.trackaudiofeatures.TrackAudioFeatures
import com.uhi5d.spotibud.util.DataState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Interactor
@Inject constructor(
    private val repository: RepositoryImpl
): UseCase{
    override suspend fun getMyTopArtists(
        token: String,
        timeRange: String,
        limit: Int?
    ): Flow<DataState<MyArtists>> {
        return repository.getMyTopArtists(token, timeRange, limit)
    }

    override suspend fun getMyTopTracks(
        token: String,
        timeRange: String,
        limit: Int?
    ): Flow<DataState<MyTracks>> {
        return repository.getMyTopTracks(token, timeRange, limit)
    }

    override suspend fun getRecommendations(
        token: String,
        seedArtists: String,
        seedTracks: String,
        market: String?
    ): Flow<DataState<Recommendations>> {
        return repository.getRecommendations(token, seedArtists, seedTracks, market)
    }

    override suspend fun getRecommendations(
        token: String,
        seedTracks: String,
        seedGenre: String,
        targetAcousticness: String,
        targetDanceability: String,
        targetEnergy: String,
        targetInstrumentalness: String,
        targetLiveness: String,
        targetValence: String,
        market: String?
    ): Flow<DataState<Recommendations>> {
        return repository.getRecommendations(token, seedTracks, seedGenre,
            targetAcousticness, targetDanceability, targetEnergy, targetInstrumentalness,
            targetLiveness, targetValence)
    }

    override suspend fun getMyProfile(token: String): Flow<DataState<CurrentUser>> {
        return repository.getMyProfile(token)
    }

    override suspend fun getMyRecentPlayed(
        token: String,
        limit: Int?
    ): Flow<DataState<RecentTracks>> {
        return repository.getMyRecentPlayed(token, limit)
    }

    override suspend fun getArtist(token: String, artistId: String): Flow<DataState<Artist>> {
        return repository.getArtist(token, artistId)
    }

    override suspend fun getSeveralArtists(token: String, ids: String): Flow<DataState<Artists>> {
        return repository.getSeveralArtists(token, ids)
    }

    override suspend fun getArtistAlbums(
        token: String,
        artistId: String,
        market: String?
    ): Flow<DataState<ArtistAlbums>> {
        return repository.getArtistAlbums(token, artistId, market)
    }

    override suspend fun getArtistRelatedArtists(
        token: String,
        artistId: String
    ): Flow<DataState<RelatedArtists>> {
        return repository.getArtistRelatedArtists(token, artistId)
    }

    override suspend fun getArtistTopTracks(
        token: String,
        artistId: String,
        market: String?
    ): Flow<DataState<ArtistTopTracks>> {
        return repository.getArtistTopTracks(token, artistId, market)
    }

    override suspend fun getTrackAudioFeature(
        token: String,
        trackId: String
    ): Flow<DataState<TrackAudioFeatures>> {
        return repository.getTrackAudioFeature(token, trackId)
    }

    override suspend fun getTrack(token: String, trackId: String): Flow<DataState<Track>> {
        return repository.getTrack(token, trackId)
    }

    override suspend fun getAlbum(token: String, albumId: String): Flow<DataState<Album>> {
        return repository.getAlbum(token, albumId)
    }

    override suspend fun search(
        token: String,
        query: String,
        type: String?,
        limit: Int?
    ): Flow<DataState<SearchResults>> {
        return repository.search(token, query, type, limit)
    }

    override suspend fun getAvailableDevices(token: String): Flow<DataState<Devices>> {
        return repository.getAvailableDevices(token)
    }

    @InternalCoroutinesApi
    override suspend fun getGenres(token: String): Flow<DataState<Genres>> {
        return repository.getGenres(token)
    }

    override suspend fun saveGenres(genres: Genres) {
        return repository.saveGenres(genres)
    }

    override fun getAlbumsTracks(
        token: String,
        albumId: String
    ): Flow<DataState<AlbumsTracksResponse>> {
        return repository.getAlbumsTracks(token, albumId)
    }

    override fun getToken(
        url:String,
        clientId: String,
        grantType: String,
        code: String,
        redirectUri: String,
        codeVerifier: String
    ): Flow<DataState<AccessToken>> {
        return repository.getToken(url,clientId, grantType, code, redirectUri, codeVerifier)
    }


}