package com.uhi5d.spotibud.data.remote

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(
    private val webService: WebService
){
    fun getToken(url:String,clientId:String,grantType:String,
                 code:String,redirectUri:String,codeVerifier:String): Flow<DataState<AccessToken>>{
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getToken(
                    url, clientId, grantType,
                    code, redirectUri, codeVerifier,
                )
               emit(DataState.Success(response.body()!!))

            }catch (e:Exception){
                emit(DataState.Fail(e))
                throw Exception(e)
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMyTopTracks(token: String, timeRange: String, limit: Int?): Flow<DataState<MyTracks>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getMyTracks(token,timeRange,limit)
                if (response.items.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMyTopArtists(token: String, timeRange: String, limit: Int?): Flow<DataState<MyArtists>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getMyArtists(token,timeRange,limit)
                if (response.items.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getRecommendations(
        token: String,
        seedArtists: String,
        seedTracks: String,
        market: String?
    ): Flow<DataState<Recommendations>> {
         return flow {
             emit(DataState.Loading)
             try {
                 val response = webService.getRecommendations(token, seedArtists, seedTracks, market)
                 if (response.seeds.isNullOrEmpty() && response.tracks.isNullOrEmpty()){
                     emit(DataState.Empty)
                 }else{
                     emit(DataState.Success(response))
                 }
             }catch (e: Exception){
                 emit(DataState.Fail(e))
             }
         }.flowOn(Dispatchers.IO)
    }

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
        market: String?
    ): Flow<DataState<Recommendations>> {
         return flow {
             emit(DataState.Loading)
             try {
                 val response = webService.getRecommendations(token, seedTracks, seedGenre,
                     targetAcousticness, targetDanceability, targetEnergy, targetInstrumentalness,
                     targetLiveness, targetValence)
                 if (response.seeds.isNullOrEmpty() && response.tracks.isNullOrEmpty()){
                     emit(DataState.Empty)
                 }else{
                     emit(DataState.Success(response))
                 }
             }catch (e: Exception){
                 emit(DataState.Fail(e))
             }
         }.flowOn(Dispatchers.IO)
    }

     suspend fun getMyProfile(token: String): Flow<DataState<CurrentUser>> {
         return flow {
             emit(DataState.Loading)
             try {
                 val response = webService.getMyProfile(token)
                 if (response.id.isNullOrEmpty()){
                     emit(DataState.Empty)
                 }else{
                     emit(DataState.Success(response))
                 }
             }catch (e: Exception){
                 emit(DataState.Fail(e))
             }
         }.flowOn(Dispatchers.IO)
    }

     suspend fun getMyRecentPlayed(
        token: String,
        limit: Int?
    ): Flow<DataState<RecentTracks>> {
         return flow {
             emit(DataState.Loading)
             try {
                 val response = webService.getMyRecentPlayed(token,limit)
                 if (response.items.isNullOrEmpty()){
                     emit(DataState.Empty)
                 }else{
                     emit(DataState.Success(response))
                 }
             }catch (e: Exception){
                 emit(DataState.Fail(e))
             }
         }.flowOn(Dispatchers.IO)
    }

     suspend fun getArtist(token: String, artistId: String): Flow<DataState<Artist>> {
         return flow {
             emit(DataState.Loading)
             try {
                 val response = webService.getArtist(token,artistId)
                 if (response.id.isNullOrEmpty()){
                     emit(DataState.Empty)
                 }else{
                     emit(DataState.Success(response))
                 }
             }catch (e: Exception){
                 emit(DataState.Fail(e))
             }
         }.flowOn(Dispatchers.IO)
    }

     suspend fun getSeveralArtists(token: String, ids: String): Flow<DataState<Artists>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getSeveralArtists(token,ids)
                if (response.artists.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getArtistAlbums(
        token: String,
        artistId: String,
        market: String?
    ): Flow<DataState<ArtistAlbums>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getArtistAlbums(token,artistId, market)
                if (response.items.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getArtistRelatedArtists(
        token: String,
        artistId: String
    ): Flow<DataState<RelatedArtists>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getArtistRelatedArtists(token,artistId)
                if (response.artists.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getArtistTopTracks(
        token: String,
        artistId: String,
        market: String?
    ): Flow<DataState<ArtistTopTracks>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getArtistTopTracks(token,artistId, market)
                if (response.tracks.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getTrackAudioFeature(
        token: String,
        trackId: String
    ): Flow<DataState<TrackAudioFeatures>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getTrackAudioFeature(token,trackId)
                if (response.id.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getTrack(token: String, trackId: String): Flow<DataState<Track>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getTrack(token,trackId)
                if (response.id.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getAlbum(token: String, albumId: String): Flow<DataState<Album>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getAlbum(token,albumId)
                if (response.id.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun search(
        token: String,
        query: String,
        type: String?,
        limit: Int?
    ): Flow<DataState<SearchResults>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.search(token, query,limit, type)
                if (response.albums?.items.isNullOrEmpty() &&
                    response.artists?.items.isNullOrEmpty() &&
                    response.tracks?.items.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun search(
        token: String,
        query: String,
        limit: Int?
    ): Flow<DataState<SearchResults>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.search(token, query,limit)
                if (response.albums?.items.isNullOrEmpty() &&
                    response.artists?.items.isNullOrEmpty() &&
                    response.tracks?.items.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getAvailableDevices(token: String): Flow<DataState<Devices>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getAvailableDevices(token)
                if (response.devices.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

     suspend fun getGenres(token: String): Flow<DataState<Genres>> {
        return flow {
            emit(DataState.Loading)
            try {
                val response = webService.getGenres(token)
                if (response.genres.isNullOrEmpty()){
                    emit(DataState.Empty)
                }else{
                    emit(DataState.Success(response))
                }
            }catch (e: Exception){
                emit(DataState.Fail(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getAlbumsTracks(token: String,albumId: String): Flow<DataState<AlbumsTracksResponse>> {
        return flow {
                emit(DataState.Loading)
               try {
                   val response = webService.getAlbumsTracks(token, albumId)
                   if (response.isSuccessful) emit(DataState.Success(response.body()!!))
               }catch (e: Exception){
                   emit(DataState.Fail(e))
               }
        }.flowOn(Dispatchers.IO)
    }

}