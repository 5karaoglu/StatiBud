package com.uhi5d.spotibud.data.remote


import com.uhi5d.spotibud.domain.model.MyArtists
import com.uhi5d.spotibud.domain.model.MyTracks
import com.uhi5d.spotibud.domain.model.Recommendations
import com.uhi5d.spotibud.domain.model.album.Album
import com.uhi5d.spotibud.domain.model.artist.Artist
import com.uhi5d.spotibud.domain.model.artistalbums.ArtistAlbums
import com.uhi5d.spotibud.domain.model.artists.Artists
import com.uhi5d.spotibud.domain.model.artisttoptracks.ArtistTopTracks
import com.uhi5d.spotibud.domain.model.currentuser.CurrentUser
import com.uhi5d.spotibud.domain.model.devices.Devices
import com.uhi5d.spotibud.domain.model.genres.Genres
import com.uhi5d.spotibud.domain.model.recenttracks.RecentTracks
import com.uhi5d.spotibud.domain.model.relatedartists.RelatedArtists
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.model.track.Track
import com.uhi5d.spotibud.domain.model.trackaudiofeatures.TrackAudioFeatures
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {


    @GET("me/top/artists")
    suspend fun getMyArtists(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int? = 50
    ): MyArtists

    @GET("me/top/tracks")
    suspend fun getMyTracks(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int? = 50
    ): MyTracks

    @GET("recommendations")
    suspend fun getRecommendations(
        @Header("Authorization") token: String,
        @Query("seed_artists") seedArtists: String,
        @Query("seed_tracks") seedTracks: String,
        @Query("market") market:String? = "US"
    ): Recommendations

    @GET("recommendations")
    suspend fun getRecommendations(
        @Header("Authorization") token: String,
        @Query("seed_tracks") seedTracks: String,
        @Query("seed_genres") seedGenre: String,
        @Query("target_acousticness") targetAcousticness: String,
        @Query("target_danceability") targetDanceability: String,
        @Query("target_energy") targetEnergy: String,
        @Query("target_instrumentalness") targetInstrumentalness: String,
        @Query("target_liveness") targetLiveness: String,
        @Query("target_valence") targetValence: String,
        @Query("market") market:String? = "US"
    ): Recommendations

    @GET("me")
    suspend fun getMyProfile(
        @Header("Authorization") token: String
    ): CurrentUser

    @GET("me/player/recently-played")
    suspend fun getMyRecentPlayed(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int? = 20
    ): RecentTracks

    @GET("artists/{id}")
    suspend fun getArtist(
        @Header("Authorization") token: String,
        @Path("id") artistId: String
    ): Artist

    @GET("artists")
    suspend fun getSeveralArtists(
        @Header("Authorization") token: String,
        @Query("ids") ids: String
    ): Artists

    @GET("artists/{id}/albums")
    suspend fun getArtistAlbums(
        @Header("Authorization") token: String,
        @Path("id") artistId: String,
        @Query("market") market:String? = "US"
    ): ArtistAlbums

    @GET("artists/{id}/related-artists")
    suspend fun getArtistRelatedArtists(
        @Header("Authorization") token: String,
        @Path("id") artistId: String
    ): RelatedArtists

    @GET("artists/{id}/top-tracks")
    suspend fun getArtistTopTracks(
        @Header("Authorization") token: String,
        @Path("id") artistId: String,
        @Query("market") market:String? = "US"
    ): ArtistTopTracks

    @GET("audio-features/{id}")
    suspend fun getTrackAudioFeature(
        @Header("Authorization") token: String,
        @Path("id") trackId: String
    ): TrackAudioFeatures

    @GET("tracks/{id}")
    suspend fun getTrack(
        @Header("Authorization") token: String,
        @Path("id") trackId: String
    ): Track

    @GET("albums/{id}")
    suspend fun getAlbum(
        @Header("Authorization") token: String,
        @Path("id") albumId: String
    ): Album

    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("type") type: String? = "artist%2album%2track",
        @Query("limit") limit: Int? = 3
    ): SearchResults


    @GET("me/player/devices")
    suspend fun getAvailableDevices(@Header("Authorization") token: String): Devices

    @GET("recommendations/available-genre-seeds")
    suspend fun getGenres(@Header("Authorization") token: String): Genres
}