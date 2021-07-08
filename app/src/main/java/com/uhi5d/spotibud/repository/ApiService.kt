package com.uhi5d.spotibud.repository

import com.uhi5d.spotibud.TrackItems
import com.uhi5d.spotibud.Tracks
import com.uhi5d.spotibud.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("me/top/artists?limit=50")
    suspend fun getMyArtists(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String
    ): Artists

    @GET("me/top/artists")
    suspend fun getMyArtistsLimited(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int
    ): Call<Artists>

    @GET("me/top/tracks?limit=50")
    suspend fun getMyTracks(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String
    ): Call<Tracks>

    @GET("me/top/tracks")
    suspend fun getMyTracksLimited(
        @Header("Authorization") token: String,
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int
    ): Call<Tracks>

    @GET("recommendations?market=US&min_popularity=50")
    suspend fun getRecommendations(
        @Header("Authorization") token: String,
        @Query("seed_artists") seedArtists: String,
        @Query("seed_tracks") seedTracks: String
    ): Call<Recommendations>

    @GET("recommendations?market=US&min_popularity=50")
    suspend fun getRecommendedTrack(
        @Header("Authorization") token: String,
        @Query("seed_tracks") seedTracks: String,
        @Query("seed_genres") seedGenre: String,
        @Query("target_acousticness") targetAcousticness: String,
        @Query("target_danceability") targetDanceability: String,
        @Query("target_energy") targetEnergy: String,
        @Query("target_instrumentalness") targetInstrumentalness: String,
        @Query("target_liveness") targetLiveness: String,
        @Query("target_valence") targetValence: String
    ): Call<Recommendations>

    @GET("me")
    suspend fun getMyProfile(@Header("Authorization") token: String): Call<User>

    @GET("me/player/recently-played?limit=20")
    suspend fun getUserRecentPlayed(@Header("Authorization") token: String): Call<RecentTracks>

    @GET("artists/{id}")
    suspend fun getArtist(
        @Header("Authorization") token: String,
        @Path("id") artistId: String
    ): Call<Item>

    @GET("artists")
    suspend fun getMultipleArtists(
        @Header("Authorization") token: String,
        @Query("ids") ids: String
    ): Call<ArtistList>

    @GET("artists/{id}/albums?include_groups=album&market=us")
    suspend fun getArtistAlbums(
        @Header("Authorization") token: String,
        @Path("id") artistId: String
    ): Call<ArtistAlbums>

    @GET("artists/{id}/related-artists")
    suspend fun getArtistRelatedArtists(
        @Header("Authorization") token: String,
        @Path("id") artistId: String
    ): Call<RelatedArtists>

    @GET("artists/{id}/top-tracks?market=us")
    suspend fun getArtistTopTracks(
        @Header("Authorization") token: String,
        @Path("id") artistId: String
    ): Call<ArtistTopTracks>

    @GET("audio-features/{id}")
    suspend fun getTrackAudioFeature(
        @Header("Authorization") token: String,
        @Path("id") trackId: String
    ): Call<TrackAudioFeatures>

    @GET("tracks/{id}")
    suspend fun getTrack(
        @Header("Authorization") token: String,
        @Path("id") trackId: String
    ): Call<TrackItems>

    @GET("albums/{id}")
    suspend fun getAlbum(
        @Header("Authorization") token: String,
        @Path("id") albumId: String
    ): Call<Album>

    @GET("search?type=artist%2Calbum%2Ctrack&limit=3")
    suspend fun search(
        @Header("Authorization") token: String,
        @Query("q") query: String
    ): Call<QueryResults>

    @GET("search")
    suspend fun searchTypeDefined(
        @Header("Authorization") token: String,
        @Query("type") type: String,
        @Query("q") query: String
    ): Call<QueryResults>

    @GET("me/player/devices")
    suspend fun getAvailableDevices(@Header("Authorization") token: String): Call<Devices>

    @GET("recommendations/available-genre-seeds")
    suspend fun getGenres(@Header("Authorization") token: String): Call<Genres>
}