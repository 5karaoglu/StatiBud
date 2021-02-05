package com.example.contestifyfirsttry

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.contestifyfirsttry.model.*
import com.example.contestifyfirsttry.util.Api
import com.example.contestifyfirsttry.util.RetrofitInstance
import com.example.contestifyfirsttry.util.Scopes
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository() {

    private val TAG = "Repository"

    var respArtistsShortTerm = MutableLiveData<Artists>()
    var respArtistsMidTerm = MutableLiveData<Artists>()
    var respArtistsLongTerm = MutableLiveData<Artists>()

    var respTracksShortTerm = MutableLiveData<Tracks>()
    var respTracksMidTerm = MutableLiveData<Tracks>()
    var respTracksLongTerm = MutableLiveData<Tracks>()

    var respUser = MutableLiveData<User>()
    var respRecentTracks = MutableLiveData<RecentTracks>()

    var respArtist = MutableLiveData<Item>()
    var respArtistTopTracks = MutableLiveData<ArtistTopTracks>()
    var respArtistAlbums = MutableLiveData<ArtistAlbums>()
    var respRelatedArtists = MutableLiveData<RelatedArtists>()

    var respTrack = MutableLiveData<TrackItems>()
    var respTrackAudioFeatures = MutableLiveData<TrackAudioFeatures>()

    var respAlbumTracks = MutableLiveData<AlbumTracks>()

    var scopes : Scopes? = null
    private val CLIENT_ID = "85e82d6c52384d2b9ada66f99f78648c"
    private val REDIRECT_URI = "http://com.example.contestifyfirsttry/callback"
    val REQUEST_CODE = 1337

    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    var service = RetrofitInstance().getRefrofitInstance()!!.create(Api::class.java)

    fun getToken(activity: Activity){
        scopes = Scopes()
        var request = AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
            .setScopes(arrayOf(scopes!!.USER_READ_PRIVATE, scopes!!.PLAYLIST_READ, scopes!!.PLAYLIST_READ_PRIVATE, scopes!!.USER_READ_PRIVATE,
                scopes!!.USER_TOP_READ,scopes!!.USER_READ_RECENTLY_PLAYED,scopes!!.USER_READ_EMAIL))
            .build();

        AuthorizationClient.openLoginActivity(activity,REQUEST_CODE,request)
    }

    fun getMyFavArtists(token:String,timeRange:String) {
        var call:retrofit2.Call<Artists> = service.getMyArtists("Bearer $token",timeRange)

        call.enqueue(object : Callback<Artists> {
            override fun onResponse(call: retrofit2.Call<Artists>, response: Response<Artists>) {
                when(timeRange){
                    "short_term" ->  respArtistsShortTerm.value = response.body()!!
                    "medium_term" ->  respArtistsMidTerm.value = response.body()!!
                    "long_term" ->  respArtistsLongTerm.value = response.body()!!
                }
            }

            override fun onFailure(call: retrofit2.Call<Artists>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getMyFavTracks(token:String,timeRange:String) {
        var call:retrofit2.Call<Tracks> = service.getMyTracks("Bearer $token",timeRange)

        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: retrofit2.Call<Tracks>, response: Response<Tracks>) {
                when(timeRange){
                    "short_term" ->  respTracksShortTerm.value = response.body()!!
                    "medium_term" ->  respTracksMidTerm.value = response.body()!!
                    "long_term" ->  respTracksLongTerm.value = response.body()!!
                }
            }

            override fun onFailure(call: retrofit2.Call<Tracks>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun playSong(context: Context,songUri:String){
        var connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

       SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
           override fun onConnected(p0: SpotifyAppRemote?) {
               mSpotifyAppRemote = p0
               Log.d(TAG, "onConnected: Connected!")

               mSpotifyAppRemote!!.playerApi.play(songUri)
           }

           override fun onFailure(p0: Throwable?) {
               Log.d(TAG, "onFailure: ${p0!!.message}")
           }

       })
    }
    fun getUser(token: String){
        var call:retrofit2.Call<User> = service.getMyProfile("Bearer $token")

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                respUser.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getRecentTracks(token: String){
        var call:retrofit2.Call<RecentTracks> = service.getUserRecentPlayed("Bearer $token")

        call.enqueue(object : Callback<RecentTracks> {
            override fun onResponse(call: Call<RecentTracks>, response: Response<RecentTracks>) {
                respRecentTracks.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<RecentTracks>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getArtist(token: String,id:String){
        var call:retrofit2.Call<Item> = service.getArtist("Bearer $token",id)

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                respArtist.value = response.body()!!
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getArtistTopTracks(token: String,id:String){
        var call:retrofit2.Call<ArtistTopTracks> = service.getArtistTopTracks("Bearer $token",id)

        call.enqueue(object : Callback<ArtistTopTracks> {
            override fun onResponse(call: Call<ArtistTopTracks>, response: Response<ArtistTopTracks>) {
                respArtistTopTracks.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<ArtistTopTracks>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getArtistAlbums(token: String,id:String){
        var call:retrofit2.Call<ArtistAlbums> = service.getArtistAlbums("Bearer $token",id)

        call.enqueue(object : Callback<ArtistAlbums> {
            override fun onResponse(call: Call<ArtistAlbums>, response: Response<ArtistAlbums>) {
                respArtistAlbums.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<ArtistAlbums>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getRelatedArtists(token: String,id:String){
        var call:retrofit2.Call<RelatedArtists> = service.getArtistRelatedArtists("Bearer $token",id)

        call.enqueue(object : Callback<RelatedArtists> {
            override fun onResponse(call: Call<RelatedArtists>, response: Response<RelatedArtists>) {
                respRelatedArtists.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<RelatedArtists>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getTrack(token: String,id:String){
        var call:retrofit2.Call<TrackItems> = service.getTrack("Bearer $token",id)

        call.enqueue(object : Callback<TrackItems> {
            override fun onResponse(call: Call<TrackItems>, response: Response<TrackItems>) {
                respTrack.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<TrackItems>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getTrackAudioFeatures(token: String,id:String){
        var call:retrofit2.Call<TrackAudioFeatures> = service.getTrackAudioFeature("Bearer $token",id)

        call.enqueue(object : Callback<TrackAudioFeatures> {
            override fun onResponse(call: Call<TrackAudioFeatures>, response: Response<TrackAudioFeatures>) {
                respTrackAudioFeatures.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<TrackAudioFeatures>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getAlbumTracks(token: String,id:String){
        var call:retrofit2.Call<AlbumTracks> = service.getAlbumTracks("Bearer $token",id)

        call.enqueue(object : Callback<AlbumTracks> {
            override fun onResponse(call: Call<AlbumTracks>, response: Response<AlbumTracks>) {
                respAlbumTracks.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<AlbumTracks>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
}