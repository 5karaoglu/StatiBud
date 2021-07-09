package com.uhi5d.spotibud.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.*
import com.uhi5d.spotibud.TrackItems
import com.uhi5d.spotibud.Tracks
import com.uhi5d.spotibud.model.*
import com.uhi5d.spotibud.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(val repository: Repository) : ViewModel() {

    var artistsListShortTerm = MutableLiveData<Artists>()
    var artistsListMidTerm = MutableLiveData<Artists>()
    var artistsListLongTerm = MutableLiveData<Artists>()

    var tracksListShortTerm = MutableLiveData<Tracks>()
    var tracksListMidTerm = MutableLiveData<Tracks>()
    var tracksListLongTerm = MutableLiveData<Tracks>()

    var recommendations = MutableLiveData<Recommendations>()

    var recentTracks = MutableLiveData<RecentTracks>()
    var user = MutableLiveData<User>()

    var artist = MutableLiveData<Item>()
    var multipleArtists = MutableLiveData<ArtistList>()
    var artistTopTracks = MutableLiveData<ArtistTopTracks>()
    var artistAlbums = MutableLiveData<ArtistAlbums>()
    var relatedArtists = MutableLiveData<RelatedArtists>()

    var track = MutableLiveData<TrackItems>()
    var trackAudioFeatures = MutableLiveData<TrackAudioFeatures>()

    var album = MutableLiveData<_root_ide_package_.com.uhi5d.spotibud.domain.model.Album>()
    var albumTracks = MutableLiveData<AlbumTracks>()

    var queryResults = MutableLiveData<QueryResults>()

    private var _searchHistory = MutableLiveData<List<SearchHistory>>()
    val searchHistory: LiveData<List<SearchHistory>>
        get() = _searchHistory

    private var _trackFinderTracks = MutableLiveData<List<TrackFinderTracks>>()
    val trackFinderTracks: LiveData<List<TrackFinderTracks>>
        get() = _trackFinderTracks

    var availableDevices = MutableLiveData<Devices>()

    var genres = MutableLiveData<Genres>()


    //room methods
    fun getAllSH() = viewModelScope.launch {
        repository.getAllSH().collect {
            _searchHistory.postValue(it)
        }
    }

    fun insertSH(searchHistory: SearchHistory) = viewModelScope.launch {
        repository.insertSH(searchHistory).collect {

        }
    }

    fun deleteSH(searchHistory: SearchHistory) = viewModelScope.launch {
        repository.deleteSH(searchHistory)
    }

    fun getAllTFT() = viewModelScope.launch {
        repository.getAllTFT().collect {
            _trackFinderTracks.postValue(it)
        }
    }

    fun insertTFT(tracks: TrackFinderTracks) = viewModelScope.launch {
        repository.insertTFT(tracks)
    }

    fun deleteAllTFT() = viewModelScope.launch {
        repository.deleteAllTFT()
    }

    //retrofit methods
    fun getMyArtists(context: Context, token: String, timeRange: String) = viewModelScope.launch {
        repository.getMyFavArtists(token, timeRange).collect {

        }
    }

    fun getMyArtistsLimited(context: Context, token: String, timeRange: String, limit: Int) {
        repository.getMyFavArtistsLimited(context, token, timeRange, limit)
    }
    fun getMyTracks(context: Context,token: String,timeRange:String){
        repository.getMyFavTracks(context, token,timeRange)
    }
    fun getMyTracksLimited(context: Context,token: String,timeRange:String,limit: Int){
        repository.getMyFavTracksLimited(context, token,timeRange,limit)
    }
    fun getRecommendations(context: Context,token: String,artistSeed:String,trackSeed:String) {
        repository.getRecommendations(context, token, artistSeed, trackSeed)
    }
    fun getRecommendedTrack(context: Context,token: String,seedTrack:String,seedGenre:String,targetAcousticness:String,targetDanceability:String,targetEnergy:String,targetInstrumentalness:String,
                            targetLiveness:String, targetValence:String) {
        repository.getRecommendedTrack(context, token,seedTrack,seedGenre, targetAcousticness, targetDanceability, targetEnergy,
            targetInstrumentalness, targetLiveness, targetValence)
    }
    fun getToken(activity:Activity){
        repository.getToken(activity)
    }
    fun getUser(context: Context,token: String){
        repository.getUser(context, token)
    }
    fun getRecentTracks(context: Context,token: String){
        repository.getRecentTracks(context, token)
    }
    fun getArtist(context: Context,token: String,id:String){
        repository.getArtist(context, token,id)
    }
    fun getMultipleArtist(context: Context,token: String,id:String){
        repository.getMultipleArtists(context, token,id)
    }
    fun getArtistTopTracks(context: Context,token: String,id:String){
        repository.getArtistTopTracks(context, token,id)
    }
    fun getArtistAlbums(context: Context, token: String,id:String){
        repository.getArtistAlbums(context,token,id)
    }
    fun getRelatedArtists(context: Context,token: String,id:String){
        repository.getRelatedArtists(context, token,id)
    }
    fun getTrack(context: Context, token: String,id:String){
        repository.getTrack(context,token,id)
    }
    fun getTrackAudioFeatures(context: Context, token: String,id:String){
        repository.getTrackAudioFeatures(context, token,id)
    }
    fun getAlbum(context: Context,token: String,id:String){
        repository.getAlbum(context, token,id)
    }
    fun getQueryResult(context: Context,token: String,q:String){
        repository.getQueryResult(context, token,q)
    }
    fun getQueryResultDefined(context: Context,token: String,type:String,q:String){
        repository.getQueryResultDefined(context, token,type,q)
    }
    fun getAvailableDevices(context: Context,token: String){
        repository.getAvailableDevices(context, token)
    }
    fun getGenres(context: Context,token: String){
        repository.getGenres(context, token)
    }


}