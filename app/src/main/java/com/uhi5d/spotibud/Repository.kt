package com.uhi5d.spotibud

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.uhi5d.spotibud.model.*
import com.uhi5d.spotibud.util.*
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(context: Context) {

    private val TAG = "Repository"

    var respArtistsShortTerm = MutableLiveData<Artists>()
    var respArtistsMidTerm = MutableLiveData<Artists>()
    var respArtistsLongTerm = MutableLiveData<Artists>()

    var respTracksShortTerm = MutableLiveData<Tracks>()
    var respTracksMidTerm = MutableLiveData<Tracks>()
    var respTracksLongTerm = MutableLiveData<Tracks>()

    var respRecommendations = MutableLiveData<Recommendations>()

    var respUser = MutableLiveData<User>()
    var respRecentTracks = MutableLiveData<RecentTracks>()

    var respArtist = MutableLiveData<Item>()
    var respMultipleArtists = MutableLiveData<ArtistList>()
    var respArtistTopTracks = MutableLiveData<ArtistTopTracks>()
    var respArtistAlbums = MutableLiveData<ArtistAlbums>()
    var respRelatedArtists = MutableLiveData<RelatedArtists>()

    var respTrack = MutableLiveData<TrackItems>()
    var respTrackAudioFeatures = MutableLiveData<TrackAudioFeatures>()

    var respAlbum = MutableLiveData<Album>()
    var respAlbumTracks = MutableLiveData<AlbumTracks>()

    var respQueryResult = MutableLiveData<QueryResults>()

    var respSearchHistory = MutableLiveData<List<SearchHistory>>()
    var respTrackFinderTracks = MutableLiveData<List<TrackFinderTracks>>()

    var respAvailableDevices = MutableLiveData<Devices>()

    var respGenres = MutableLiveData<Genres>()

    var scopes : Scopes? = null
    private val CLIENT_ID = "d3810deed3744f56b5cc8a5a905c803f"
    private val REDIRECT_URI = "http://com.uhi5d.spotibud/callback"
    val REQUEST_CODE = 1337

    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    private var service: Api = RetrofitInstance().getRefrofitInstance()!!.create(Api::class.java)
    private val db = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "searchHistory").build()
    private val tfDb = Room.databaseBuilder(context.applicationContext, TrackFinderDatabase::class.java,"trackfindertracks").build()


    //room methods
    //Search History Database
    fun getAll(){
        respSearchHistory.postValue(db.dao().getAll())
    }
    fun insert(searchHistory: SearchHistory){
        db.dao().insert(searchHistory)
    }
    fun delete(searchHistory: SearchHistory){
        db.dao().delete(searchHistory)
    }
    //TrackFinder Database
    fun trackFinderGetAll(){
        respTrackFinderTracks.postValue(tfDb.trackFinderDao().getAll())
    }
    fun trackFinderInsert(tracks: TrackFinderTracks){
        tfDb.trackFinderDao().insert(tracks)
    }
    fun trackFinderDeleteAll(){
        tfDb.trackFinderDao().deleteAll()
    }

    fun getToken(activity: Activity){
        scopes = Scopes()
        var request = AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
            .setScopes(arrayOf(scopes!!.USER_READ_PRIVATE, scopes!!.PLAYLIST_READ, scopes!!.PLAYLIST_READ_PRIVATE, scopes!!.USER_READ_PRIVATE,
                scopes!!.USER_TOP_READ,scopes!!.USER_READ_RECENTLY_PLAYED,scopes!!.USER_READ_EMAIL,scopes!!.USER_READ_PLAYBACK_STATE))
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

    fun getMyFavArtistsLimited(token:String, timeRange:String, limit: Int) {
        var call:retrofit2.Call<Artists> = service.getMyArtistsLimited("Bearer $token",timeRange,limit)
        Log.d(TAG, "getMyFavArtistsLimited: ${call.request()}")
        call.enqueue(object : Callback<Artists> {
            override fun onResponse(call: retrofit2.Call<Artists>, response: Response<Artists>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    respArtistsShortTerm.value = response.body()!!
                } else {
                    Log.d(TAG, "onResponseRecommendations: ${response.body()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<Artists>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
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

    fun getMyFavTracksLimited(token:String, timeRange:String, limit: Int) {
        var call:retrofit2.Call<Tracks> = service.getMyTracksLimited("Bearer $token",timeRange,limit)
        Log.d(TAG, "getMyFavTracksLimited: ${call.request()}")
        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: retrofit2.Call<Tracks>, response: Response<Tracks>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                respTracksShortTerm.value = response.body()!!
            }

            override fun onFailure(call: retrofit2.Call<Tracks>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getRecommendations(token: String,seedArtist:String,seedTrack:String){
        val call: Call<Recommendations> = service.getRecommendations("Bearer $token",seedArtist,seedTrack)
        Log.d(TAG, "getRecommendations: ${call.request()}")
        call.enqueue(object : Callback<Recommendations> {
            override fun onResponse(
                call: Call<Recommendations>,
                response: Response<Recommendations>
            ) {
                if (response.isSuccessful) {
                    respRecommendations.value = response.body()!!
                } else
                    Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<Recommendations>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getRecommendedTrack(token: String,seedTrack:String,seedGenre:String,targetAcousticness:String,targetDanceability:String,targetEnergy:String,targetInstrumentalness:String,
                            targetLiveness:String,targetValence:String){
        val call: Call<Recommendations> = service.getRecommendedTrack("Bearer $token",seedTrack,seedGenre,targetAcousticness,
            targetDanceability, targetEnergy, targetInstrumentalness, targetLiveness, targetValence)
        Log.d(TAG, "getRecommendedTrack: ${call.request()}")
        call.enqueue(object : Callback<Recommendations> {
            override fun onResponse(
                call: Call<Recommendations>,
                response: Response<Recommendations>
            ) {
                if (response.isSuccessful) {
                    respRecommendations.value = response.body()!!
                    Log.d(TAG, "onResponse: ${response.body()}")
                } else
                    Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<Recommendations>, t: Throwable) {
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
                if (response.isSuccessful){
                respRecentTracks.value = response.body()!!}
                Log.d(TAG, "onResponseRecent: $response")
            }

            override fun onFailure(call: Call<RecentTracks>, t: Throwable) {
                Log.d(TAG, "onFailureRecent: ${t.message}")
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

    fun getMultipleArtists(token: String,ids:String){
        var call:retrofit2.Call<ArtistList> = service.getMultipleArtists("Bearer $token",ids)

        call.enqueue(object : Callback<ArtistList> {
            override fun onResponse(call: Call<ArtistList>, response: Response<ArtistList>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                respMultipleArtists.value = response.body()!!
            }

            override fun onFailure(call: Call<ArtistList>, t: Throwable) {
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
                Log.d(TAG, "onResponse: ${response.body()}")
                respTrack.value = response.body()!!

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
    fun getAlbum(token: String,id:String){
        var call:retrofit2.Call<Album> = service.getAlbum("Bearer $token",id)
        Log.d(TAG, "getAlbum: called")
        call.enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                respAlbum.value = response.body()!!
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
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
    fun getQueryResult(token: String,q:String) {
        var call: retrofit2.Call<QueryResults> = service.search("Bearer $token", q)
        Log.d(TAG, "getQueryResult: ${call.request().url()}")
        call.enqueue(object : Callback<QueryResults> {
            override fun onResponse(call: Call<QueryResults>, response: Response<QueryResults>) {
                if (response.isSuccessful) {
                    respQueryResult.value = response.body()!!
                    Log.d(TAG, "onResponse: ${response.body()}")
                }
            }

            override fun onFailure(call: Call<QueryResults>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getQueryResultDefined(token: String,type:String,q:String){
        var call:retrofit2.Call<QueryResults> = service.searchTypeDefined("Bearer $token",type,q)

        call.enqueue(object : Callback<QueryResults> {
            override fun onResponse(call: Call<QueryResults>, response: Response<QueryResults>) {
                if (response.isSuccessful) {
                    respQueryResult.value = response.body()!!
                }
                Log.d(TAG, "onResponse: $q $type")
                Log.d(TAG, "onResponse: ${response.body()}")
                Log.d(TAG, "onResponse: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<QueryResults>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }
    fun getAvailableDevices(token: String){
        var call: Call<Devices> = service.getAvailableDevices("Bearer $token")

        call.enqueue(object : Callback<Devices> {
            override fun onResponse(call: Call<Devices>, response: Response<Devices>) {
                if (response.isSuccessful) {
                    respAvailableDevices.value = response.body()!!
                    Log.d(TAG, "onResponse: ${response.body()}")
                }

                Log.d(TAG, "onResponse: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<Devices>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getGenres(token: String){
        var call: Call<Genres> = service.getGenres("Bearer $token")
        Log.d(TAG, "getGenres: ${call.request()}")
        call.enqueue(object : Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                    respGenres.value = response.body()!!
                    Log.d(TAG, "onResponse: ${response.body()}")
                Log.d(TAG, "onResponse: ${response.message()}")
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })
    }

}