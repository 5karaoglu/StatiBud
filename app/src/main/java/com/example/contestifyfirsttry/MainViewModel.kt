package com.example.contestifyfirsttry

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.contestifyfirsttry.model.*

class MainViewModel(lifecycleOwner: LifecycleOwner,context: Context) : ViewModel() {
    var artistsListShortTerm = MutableLiveData<Artists>()
    var artistsListMidTerm = MutableLiveData<Artists>()
    var artistsListLongTerm = MutableLiveData<Artists>()

    var tracksListShortTerm = MutableLiveData<Tracks>()
    var tracksListMidTerm = MutableLiveData<Tracks>()
    var tracksListLongTerm = MutableLiveData<Tracks>()

    var repository : Repository = Repository(context)
    var recentTracks = MutableLiveData<RecentTracks>()
    var user = MutableLiveData<User>()

    var artist = MutableLiveData<Item>()
    var multipleArtists = MutableLiveData<ArtistList>()
    var artistTopTracks = MutableLiveData<ArtistTopTracks>()
    var artistAlbums = MutableLiveData<ArtistAlbums>()
    var relatedArtists = MutableLiveData<RelatedArtists>()

    var track = MutableLiveData<TrackItems>()
    var trackAudioFeatures = MutableLiveData<TrackAudioFeatures>()

    var album = MutableLiveData<Album>()
    var albumTracks = MutableLiveData<AlbumTracks>()

    var queryResults = MutableLiveData<QueryResults>()

    var searchHistory = MutableLiveData<List<SearchHistory>>()

    init {
        repository.respArtistsShortTerm.observe(lifecycleOwner,object : Observer<Artists>{
            override fun onChanged(t: Artists?) {
                artistsListShortTerm.value = t
            }
        })
        repository.respArtistsMidTerm.observe(lifecycleOwner,object : Observer<Artists>{
            override fun onChanged(t: Artists?) {
                artistsListMidTerm.value = t
            }
        })
        repository.respArtistsLongTerm.observe(lifecycleOwner,object : Observer<Artists>{
            override fun onChanged(t: Artists?) {
                artistsListLongTerm.value = t
            }
        })

        repository.respTracksShortTerm.observe(lifecycleOwner,object : Observer<Tracks>{
            override fun onChanged(t: Tracks?) {
                tracksListShortTerm.value = t
            }
        })
        repository.respTracksMidTerm.observe(lifecycleOwner,object : Observer<Tracks>{
            override fun onChanged(t: Tracks?) {
                tracksListMidTerm.value = t
            }
        })
        repository.respTracksLongTerm.observe(lifecycleOwner,object : Observer<Tracks>{
            override fun onChanged(t: Tracks?) {
                tracksListLongTerm.value = t
            }
        })

        repository.respUser.observe(lifecycleOwner,object : Observer<User>{
            override fun onChanged(t: User?) {
                user.value = t
            }
        })
        repository.respRecentTracks.observe(lifecycleOwner,object : Observer<RecentTracks>{
            override fun onChanged(t: RecentTracks?) {
                recentTracks.value = t
            }
        })
        repository.respArtist.observe(lifecycleOwner,object : Observer<Item>{
            override fun onChanged(t: Item?) {
                artist.value = t
            }
        })
        repository.respMultipleArtists.observe(lifecycleOwner,object : Observer<ArtistList>{
            override fun onChanged(t: ArtistList?) {
                multipleArtists.value = t
            }
        })
        repository.respArtistTopTracks.observe(lifecycleOwner,object : Observer<ArtistTopTracks>{
            override fun onChanged(t: ArtistTopTracks?) {
                artistTopTracks.value = t
            }
        })
        repository.respArtistAlbums.observe(lifecycleOwner,object : Observer<ArtistAlbums>{
            override fun onChanged(t: ArtistAlbums?) {
                artistAlbums.value = t
            }
        })
        repository.respRelatedArtists.observe(lifecycleOwner,object : Observer<RelatedArtists>{
            override fun onChanged(t: RelatedArtists?) {
                relatedArtists.value = t
            }
        })
        repository.respTrack.observe(lifecycleOwner,object : Observer<TrackItems>{
            override fun onChanged(t: TrackItems?) {
                track.value = t
            }
        })
        repository.respTrackAudioFeatures.observe(lifecycleOwner,object : Observer<TrackAudioFeatures>{
            override fun onChanged(t: TrackAudioFeatures?) {
                trackAudioFeatures.value = t
            }
        })
        repository.respAlbumTracks.observe(lifecycleOwner,object : Observer<AlbumTracks>{
            override fun onChanged(t: AlbumTracks?) {
                albumTracks.value = t
            }
        })
        repository.respAlbum.observe(lifecycleOwner,object : Observer<Album>{
            override fun onChanged(t: Album?) {
                album.value = t
            }
        })
        repository.respQueryResult.observe(lifecycleOwner,object : Observer<QueryResults>{
            override fun onChanged(t: QueryResults?) {
                queryResults.value = t
            }
        })
        repository.respSearchHistory.observe(lifecycleOwner,object : Observer<List<SearchHistory>>{
            override fun onChanged(t: List<SearchHistory>?) {
                searchHistory.value = t
            }
        })

    }
    //room methods
    fun getAll(){
        repository.getAll()
    }
    fun insert(searchHistory: SearchHistory){
        repository.insert(searchHistory)
    }
    fun delete(searchHistory: SearchHistory){
        repository.delete(searchHistory)
    }

    fun getMyArtists(token: String,timeRange:String){
        repository.getMyFavArtists(token,timeRange)
    }
    fun getMyTracks(token: String,timeRange:String){
        repository.getMyFavTracks(token,timeRange)
    }

    fun getToken(activity:Activity){
        repository.getToken(activity)
    }

    fun playSong(context: Context,songUri:String){
        repository.playSong(context,songUri)
    }
    fun getUser(token: String){
        repository.getUser(token)
    }
    fun getRecentTracks(token: String){
        repository.getRecentTracks(token)
    }
    fun getArtist(token: String,id:String){
        repository.getArtist(token,id)
    }
    fun getMultipleArtist(token: String,id:String){
        repository.getMultipleArtists(token,id)
    }
    fun getArtistTopTracks(token: String,id:String){
        repository.getArtistTopTracks(token,id)
    }
    fun getArtistAlbums(token: String,id:String){
        repository.getArtistAlbums(token,id)
    }
    fun getRelatedArtists(token: String,id:String){
        repository.getRelatedArtists(token,id)
    }
    fun getTrack(token: String,id:String){
        repository.getTrack(token,id)
    }
    fun getTrackAudioFeatures(token: String,id:String){
        repository.getTrackAudioFeatures(token,id)
    }
    fun getAlbum(token: String,id:String){
        repository.getAlbum(token,id)
    }
    fun getAlbumTracks(token: String,id:String){
        repository.getAlbumTracks(token,id)
    }
    fun getQueryResult(token: String,q:String){
        repository.getQueryResult(token,q)
    }
    fun getQueryResultDefined(token: String,type:String,q:String){
        repository.getQueryResultDefined(token,type,q)
    }


}