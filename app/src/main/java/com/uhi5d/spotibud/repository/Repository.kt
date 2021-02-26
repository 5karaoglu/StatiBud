package com.uhi5d.spotibud.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.uhi5d.spotibud.model.*
import com.uhi5d.spotibud.util.*
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.uhi5d.spotibud.TrackItems
import com.uhi5d.spotibud.Tracks
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
    private val REQUEST_CODE = 1337

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
        val request = AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
            .setScopes(arrayOf(scopes!!.USER_READ_PRIVATE, scopes!!.PLAYLIST_READ, scopes!!.PLAYLIST_READ_PRIVATE, scopes!!.USER_READ_PRIVATE,
                scopes!!.USER_TOP_READ,scopes!!.USER_READ_RECENTLY_PLAYED,scopes!!.USER_READ_EMAIL,scopes!!.USER_READ_PLAYBACK_STATE))
            .build()

        AuthorizationClient.openLoginActivity(activity,REQUEST_CODE,request)
    }

    fun getMyFavArtists(context: Context,token:String,timeRange:String) {
        val call: Call<Artists> = service.getMyArtists("Bearer $token",timeRange)

        call.enqueue(object : Callback<Artists> {
            override fun onResponse(call: Call<Artists>, response: Response<Artists>) {
                if (response.isSuccessful) {
                    when (timeRange) {
                        "short_term" -> respArtistsShortTerm.value = response.body()!!
                        "medium_term" -> respArtistsMidTerm.value = response.body()!!
                        "long_term" -> respArtistsLongTerm.value = response.body()!!
                    }
                } else {
                    Toast.makeText(context, "Top Artists Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Artists>, t: Throwable) {
                Toast.makeText(context, "Top Artists Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMyFavArtistsLimited(context: Context,token:String, timeRange:String, limit: Int) {
        val call:Call<Artists> = service.getMyArtistsLimited("Bearer $token",timeRange,limit)

        call.enqueue(object : Callback<Artists> {
            override fun onResponse(call: Call<Artists>, response: Response<Artists>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    respArtistsShortTerm.value = response.body()!!
                } else {
                    Toast.makeText(context, "Top Artists Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Artists>, t: Throwable) {
                Toast.makeText(context, "Top Artists Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMyFavTracks(context: Context,token:String,timeRange:String) {
        val call:Call<Tracks> = service.getMyTracks("Bearer $token",timeRange)

        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                if (response.isSuccessful){
                    when(timeRange){
                        "short_term" ->  respTracksShortTerm.value = response.body()!!
                        "medium_term" ->  respTracksMidTerm.value = response.body()!!
                        "long_term" ->  respTracksLongTerm.value = response.body()!!
                    }
                }else{
                    Toast.makeText(context, "Top Tracks Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Tracks>, t: Throwable) {
                Toast.makeText(context, "Top Tracks Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMyFavTracksLimited(context: Context,token:String, timeRange:String, limit: Int) {
        val call: Call<Tracks> = service.getMyTracksLimited("Bearer $token",timeRange,limit)
        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                if (response.isSuccessful){
                    respTracksShortTerm.value = response.body()!!
                }else{
                    Toast.makeText(context, "Top Tracks Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Tracks>, t: Throwable) {
                Toast.makeText(context, "Top Tracks Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getRecommendations(context: Context,token: String,seedArtist:String,seedTrack:String){
        val call: Call<Recommendations> = service.getRecommendations("Bearer $token",seedArtist,seedTrack)
        call.enqueue(object : Callback<Recommendations> {
            override fun onResponse(
                call: Call<Recommendations>,
                response: Response<Recommendations>
            ) {
                if (response.isSuccessful) {
                    respRecommendations.value = response.body()!!
                } else
                    Toast.makeText(context, "Recommendations Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Recommendations>, t: Throwable) {
                Toast.makeText(context, "Recommendations Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getRecommendedTrack(context: Context,token: String,seedTrack:String,seedGenre:String,targetAcousticness:String,targetDanceability:String,targetEnergy:String,targetInstrumentalness:String,
                            targetLiveness:String,targetValence:String){
        val call: Call<Recommendations> = service.getRecommendedTrack("Bearer $token",seedTrack,seedGenre,targetAcousticness,
            targetDanceability, targetEnergy, targetInstrumentalness, targetLiveness, targetValence)

        call.enqueue(object : Callback<Recommendations> {
            override fun onResponse(
                call: Call<Recommendations>,
                response: Response<Recommendations>
            ) {
                if (response.isSuccessful) {
                    respRecommendations.value = response.body()!!
                } else
                    Toast.makeText(context, "Recommended Track Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<Recommendations>, t: Throwable) {
                Toast.makeText(context, "Recommended Track Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getUser(context: Context,token: String){
        val call: Call<User> = service.getMyProfile("Bearer $token")

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    respUser.value = response.body()!!
                }else{
                    Toast.makeText(context, "User Info Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, "User Info Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getRecentTracks(context: Context,token: String){
        val call: Call<RecentTracks> = service.getUserRecentPlayed("Bearer $token")

        call.enqueue(object : Callback<RecentTracks> {
            override fun onResponse(call: Call<RecentTracks>, response: Response<RecentTracks>) {
                if (response.isSuccessful){
                respRecentTracks.value = response.body()!!
                }else{
                    Toast.makeText(context, "Recent Tracks Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RecentTracks>, t: Throwable) {
                Toast.makeText(context, "Recent Tracks Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getArtist(context: Context,token: String,id:String){
        val call: Call<Item> = service.getArtist("Bearer $token",id)

        call.enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful){
                    respArtist.value = response.body()!!
                }else{
                    Toast.makeText(context, "Artist Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Toast.makeText(context, "Artist Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMultipleArtists(context: Context,token: String,ids:String){
        val call: Call<ArtistList> = service.getMultipleArtists("Bearer $token",ids)

        call.enqueue(object : Callback<ArtistList> {
            override fun onResponse(call: Call<ArtistList>, response: Response<ArtistList>) {
                if (response.isSuccessful){
                respMultipleArtists.value = response.body()!!
                }else{
                    Toast.makeText(context, "Multiple Artists Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ArtistList>, t: Throwable) {
                Toast.makeText(context, "Multiple Artists Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getArtistTopTracks(context: Context,token: String,id:String){
        val call: Call<ArtistTopTracks> = service.getArtistTopTracks("Bearer $token",id)

        call.enqueue(object : Callback<ArtistTopTracks> {
            override fun onResponse(call: Call<ArtistTopTracks>, response: Response<ArtistTopTracks>) {
                if (response.isSuccessful){
                    respArtistTopTracks.value = response.body()!!
                }else{
                    Toast.makeText(context, "Artist Top Tracks Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ArtistTopTracks>, t: Throwable) {
                Toast.makeText(context, "Artist Top Tracks Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getArtistAlbums(context: Context,token: String,id:String){
        val call: Call<ArtistAlbums> = service.getArtistAlbums("Bearer $token",id)

        call.enqueue(object : Callback<ArtistAlbums> {
            override fun onResponse(call: Call<ArtistAlbums>, response: Response<ArtistAlbums>) {
                if (response.isSuccessful){
                    respArtistAlbums.value = response.body()!!
                }else{
                    Toast.makeText(context, "Artist Albums Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ArtistAlbums>, t: Throwable) {
                Toast.makeText(context, "Artist Albums Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getRelatedArtists(context: Context,token: String,id:String){
        val call: Call<RelatedArtists> = service.getArtistRelatedArtists("Bearer $token",id)

        call.enqueue(object : Callback<RelatedArtists> {
            override fun onResponse(call: Call<RelatedArtists>, response: Response<RelatedArtists>) {
                if (response.isSuccessful){
                    respRelatedArtists.value = response.body()!!
                }else{
                    Toast.makeText(context, "Related Artists Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RelatedArtists>, t: Throwable) {
                Toast.makeText(context, "Related Artists Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getTrack(context: Context,token: String,id:String){
        val call: Call<TrackItems> = service.getTrack("Bearer $token",id)

        call.enqueue(object : Callback<TrackItems> {
            override fun onResponse(call: Call<TrackItems>, response: Response<TrackItems>) {
                if (response.isSuccessful){
                    respTrack.value = response.body()!!
                }else{
                    Toast.makeText(context, "Track Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<TrackItems>, t: Throwable) {
                Toast.makeText(context, "Track Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getTrackAudioFeatures(context: Context,token: String,id:String){
        val call: Call<TrackAudioFeatures> = service.getTrackAudioFeature("Bearer $token",id)

        call.enqueue(object : Callback<TrackAudioFeatures> {
            override fun onResponse(call: Call<TrackAudioFeatures>, response: Response<TrackAudioFeatures>) {
                if (response.isSuccessful){
                    respTrackAudioFeatures.value = response.body()!!
                }else{
                    Toast.makeText(context, "Audio Features Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<TrackAudioFeatures>, t: Throwable) {
                Toast.makeText(context, "Audio Features Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getAlbum(context: Context,token: String,id:String){
        val call: Call<Album> = service.getAlbum("Bearer $token",id)

        call.enqueue(object : Callback<Album> {
            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful){
                    respAlbum.value = response.body()!!
                }else{
                    Toast.makeText(context, "Album Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Album>, t: Throwable) {
                Toast.makeText(context, "Album Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getQueryResult(context: Context,token: String,q:String) {
        val call: Call<QueryResults> = service.search("Bearer $token", q)

        call.enqueue(object : Callback<QueryResults> {
            override fun onResponse(call: Call<QueryResults>, response: Response<QueryResults>) {
                if (response.isSuccessful) {
                    respQueryResult.value = response.body()!!
                }else{
                    Toast.makeText(context, "Query Result Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QueryResults>, t: Throwable) {
                Toast.makeText(context, "Query Result Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getQueryResultDefined(context: Context,token: String,type:String,q:String){
        val call: Call<QueryResults> = service.searchTypeDefined("Bearer $token",type,q)

        call.enqueue(object : Callback<QueryResults> {
            override fun onResponse(call: Call<QueryResults>, response: Response<QueryResults>) {
                if (response.isSuccessful) {
                    respQueryResult.value = response.body()!!
                }else{
                    Toast.makeText(context, "Query Result Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QueryResults>, t: Throwable) {
                Toast.makeText(context, "Query Result Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun getAvailableDevices(context: Context,token: String){
        val call: Call<Devices> = service.getAvailableDevices("Bearer $token")

        call.enqueue(object : Callback<Devices> {
            override fun onResponse(call: Call<Devices>, response: Response<Devices>) {
                if (response.isSuccessful) {
                    respAvailableDevices.value = response.body()!!
                }else{
                    Toast.makeText(context, "Available Devices Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Devices>, t: Throwable) {
                Toast.makeText(context, "Available Devices Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getGenres(context: Context,token: String){
        val call: Call<Genres> = service.getGenres("Bearer $token")

        call.enqueue(object : Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                if (response.isSuccessful){
                    respGenres.value = response.body()!!
                }else{
                    Toast.makeText(context, "Genres Error. Code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Genres>, t: Throwable) {
                Toast.makeText(context, "Genres Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}