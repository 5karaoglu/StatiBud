package com.uhi5d.spotibud.data.repository

import com.uhi5d.spotibud.data.local.LocalDataSource
import com.uhi5d.spotibud.data.remote.RemoteDataSource
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
import com.uhi5d.spotibud.domain.repository.Repository
import com.uhi5d.spotibud.util.DataState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl
    @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
): Repository {
    override suspend fun getMyTopArtists(
        token: String,
        timeRange: String,
        limit: Int?
    ): Flow<DataState<MyArtists>> {
        return remoteDataSource.getMyTopArtists(token, timeRange, limit)
    }

    override suspend fun getMyTopTracks(
        token: String,
        timeRange: String,
        limit: Int?
    ): Flow<DataState<MyTracks>> {
        return remoteDataSource.getMyTopTracks(token, timeRange, limit)
    }

    override suspend fun getRecommendations(
        token: String,
        seedArtists: String,
        seedTracks: String,
        market: String?
    ): Flow<DataState<Recommendations>> {
        return remoteDataSource.getRecommendations(token, seedArtists, seedTracks, market)
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
        return remoteDataSource.getRecommendations(token, seedTracks, seedGenre,
            targetAcousticness, targetDanceability, targetEnergy, targetInstrumentalness,
            targetLiveness, targetValence, market)
    }

    override suspend fun getMyProfile(token: String): Flow<DataState<CurrentUser>> {
        return remoteDataSource.getMyProfile(token)
    }

    override suspend fun getMyRecentPlayed(
        token: String,
        limit: Int?
    ): Flow<DataState<RecentTracks>> {
        return remoteDataSource.getMyRecentPlayed(token, limit)
    }

    override suspend fun getArtist(token: String, artistId: String): Flow<DataState<Artist>> {
        return remoteDataSource.getArtist(token, artistId)
    }

    override suspend fun getSeveralArtists(token: String, ids: String): Flow<DataState<Artists>> {
        return remoteDataSource.getSeveralArtists(token, ids)
    }

    override suspend fun getArtistAlbums(
        token: String,
        artistId: String,
        market: String?
    ): Flow<DataState<ArtistAlbums>> {
        return remoteDataSource.getArtistAlbums(token, artistId, market)
    }

    override suspend fun getArtistRelatedArtists(
        token: String,
        artistId: String
    ): Flow<DataState<RelatedArtists>> {
        return remoteDataSource.getArtistRelatedArtists(token, artistId)
    }

    override suspend fun getArtistTopTracks(
        token: String,
        artistId: String,
        market: String?
    ): Flow<DataState<ArtistTopTracks>> {
        return remoteDataSource.getArtistTopTracks(token, artistId, market)
    }

    override suspend fun getTrackAudioFeature(
        token: String,
        trackId: String
    ): Flow<DataState<TrackAudioFeatures>> {
        return remoteDataSource.getTrackAudioFeature(token, trackId)
    }

    override suspend fun getTrack(token: String, trackId: String): Flow<DataState<Track>> {
        return remoteDataSource.getTrack(token, trackId)
    }

    override suspend fun getAlbum(token: String, albumId: String): Flow<DataState<Album>> {
        return remoteDataSource.getAlbum(token, albumId)
    }

    override suspend fun search(
        token: String,
        query: String,
        type: String?,
        limit: Int?
    ): Flow<DataState<SearchResults>> {
        return remoteDataSource.search(token, query, type, limit)
    }

    override suspend fun getAvailableDevices(token: String): Flow<DataState<Devices>> {
        return remoteDataSource.getAvailableDevices(token)
    }

    @InternalCoroutinesApi
    override suspend fun getGenres(token: String): Flow<DataState<Genres>> = flow {
        val genres = localDataSource.getAllGenres()
        if(genres.genres.isEmpty()){
            remoteDataSource.getGenres(token).collect { state ->
                when(state){
                    is DataState.Success -> {
                        saveGenres(state.data)
                        emit(DataState.Success(localDataSource.getAllGenres()))
                    }
                    is DataState.Fail -> emit(state)
                }
            }
        }else{
            emit(DataState.Success(genres))
        }
    }

    override suspend fun saveGenres(genres: Genres) {
        localDataSource.saveGenres(genres)
    }

    override fun getAlbumsTracks(
        token: String,
        albumId: String
    ): Flow<DataState<AlbumsTracksResponse>> {
        return remoteDataSource.getAlbumsTracks(token, albumId)
    }

    override fun getToken(
        url:String,
        clientId: String,
        grantType: String,
        code: String,
        redirectUri: String,
        codeVerifier: String
    ): Flow<DataState<AccessToken>> {
        return remoteDataSource.getToken(url,clientId, grantType, code, redirectUri, codeVerifier)
    }
}