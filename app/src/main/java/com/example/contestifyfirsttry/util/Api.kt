package com.example.contestifyfirsttry.util

import com.example.contestifyfirsttry.model.Artists
import com.example.contestifyfirsttry.TrackItems
import com.example.contestifyfirsttry.Tracks
import com.example.contestifyfirsttry.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("me/top/artists?limit=50")
    fun getMyArtists(@Header("Authorization") token:String, @Query("time_range") timeRange:String):Call<Artists>

    @GET("me/top/artists")
    fun getMyArtistsLimited(@Header("Authorization") token:String, @Query("time_range") timeRange:String, @Query("limit") limit: Int):Call<Artists>

    @GET("me/top/tracks?limit=50")
    fun getMyTracks(@Header("Authorization") token:String, @Query("time_range") timeRange:String):Call<Tracks>

    @GET("me/top/tracks")
    fun getMyTracksLimited(@Header("Authorization") token:String, @Query("time_range") timeRange:String,@Query("limit") limit:Int):Call<Tracks>

    @GET("recommendations?market=US&min_popularity=50")
    fun getRecommendations(@Header("Authorization") token:String,
                           @Query("seed_artists") seedArtists:String,
                           @Query("seed_tracks") seedTracks:String):Call<Recommendations>

    @GET("recommendations?market=US&min_popularity=50")
    fun getRecommendedTrack(@Header("Authorization") token:String,
                            @Query("seed_tracks") seedTracks:String,
                            @Query("seed_genres") seedGenre:String,
                            @Query("target_acousticness") targetAcousticness:String,
                            @Query("target_danceability") targetDanceability:String,
                            @Query("target_energy") targetEnergy:String,
                            @Query("target_instrumentalness") targetInstrumentalness:String,
                            @Query("target_liveness") targetLiveness:String,
                            @Query("target_valence") targetValence:String):Call<Recommendations>

    @GET("me")
    fun getMyProfile(@Header("Authorization") token:String):Call<User>

    @GET("me/player/recently-played?limit=20")
    fun getUserRecentPlayed(@Header("Authorization") token:String):Call<RecentTracks>

    @GET("artists/{id}")
    fun getArtist(@Header("Authorization") token:String, @Path("id") artistId:String):Call<Item>

    @GET("artists")
    fun getMultipleArtists(@Header("Authorization") token:String, @Query("ids") ids:String):Call<ArtistList>

    @GET("artists/{id}/albums?include_groups=album&market=us")
    fun getArtistAlbums(@Header("Authorization") token:String, @Path("id") artistId:String):Call<ArtistAlbums>

    @GET("artists/{id}/related-artists")
    fun getArtistRelatedArtists(@Header("Authorization") token:String, @Path("id") artistId:String):Call<RelatedArtists>

    @GET("artists/{id}/top-tracks?market=us")
    fun getArtistTopTracks(@Header("Authorization") token:String, @Path("id") artistId:String):Call<ArtistTopTracks>

    @GET("audio-features/{id}")
    fun getTrackAudioFeature(@Header("Authorization") token:String, @Path("id") trackId:String):Call<TrackAudioFeatures>

    @GET("tracks/{id}")
    fun getTrack(@Header("Authorization") token:String, @Path("id") trackId:String):Call<TrackItems>

    @GET("albums/{id}")
    fun getAlbum(@Header("Authorization") token:String, @Path("id") albumId:String):Call<Album>

    @GET("albums/{id}/tracks")
    fun getAlbumTracks(@Header("Authorization") token:String, @Path("id") albumId:String):Call<AlbumTracks>

    @GET("search?type=artist%2Calbum%2Ctrack&limit=3")
    fun search(@Header("Authorization") token:String, @Query("q") query:String):Call<QueryResults>

    @GET("search")
    fun searchTypeDefined(@Header("Authorization") token:String, @Query("type") type:String, @Query("q") query:String):Call<QueryResults>

    @GET("me/player/devices")
    fun getAvailableDevices(@Header("Authorization") token:String):Call<Devices>

    @GET("recommendations/available-genre-seeds")
    fun getGenres(@Header("Authorization") token:String):Call<Genres>
}