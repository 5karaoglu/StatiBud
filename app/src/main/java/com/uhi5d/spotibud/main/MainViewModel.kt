package com.uhi5d.spotibud.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uhi5d.spotibud.Repository
import com.uhi5d.spotibud.TrackItems
import com.uhi5d.spotibud.Tracks
import com.uhi5d.spotibud.model.*

class MainViewModel(lifecycleOwner: LifecycleOwner,context: Context) : ViewModel() {
    var artistsListShortTerm = MutableLiveData<Artists>()
    var artistsListMidTerm = MutableLiveData<Artists>()
    var artistsListLongTerm = MutableLiveData<Artists>()

    var tracksListShortTerm = MutableLiveData<Tracks>()
    var tracksListMidTerm = MutableLiveData<Tracks>()
    var tracksListLongTerm = MutableLiveData<Tracks>()

    var recommendations = MutableLiveData<Recommendations>()

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
    var trackFinderTracks = MutableLiveData<List<TrackFinderTracks>>()

    var availableDevices = MutableLiveData<Devices>()

    var genres = MutableLiveData<Genres>()

    init {
        repository.respArtistsShortTerm.observe(lifecycleOwner,
            { t -> artistsListShortTerm.value = t })
        repository.respArtistsMidTerm.observe(lifecycleOwner,
            { t -> artistsListMidTerm.value = t })
        repository.respArtistsLongTerm.observe(lifecycleOwner,
            { t -> artistsListLongTerm.value = t })

        repository.respTracksShortTerm.observe(lifecycleOwner,
            { t -> tracksListShortTerm.value = t })
        repository.respTracksMidTerm.observe(lifecycleOwner,
            { t -> tracksListMidTerm.value = t })
        repository.respTracksLongTerm.observe(lifecycleOwner,
            { t -> tracksListLongTerm.value = t })

        repository.respRecommendations.observe(lifecycleOwner,
            { t -> recommendations.value = t })

        repository.respUser.observe(lifecycleOwner,
            { t -> user.value = t })

        repository.respRecentTracks.observe(lifecycleOwner,
            { t -> recentTracks.value = t })

        repository.respArtist.observe(lifecycleOwner,
            { t -> artist.value = t })

        repository.respMultipleArtists.observe(lifecycleOwner,
            { t -> multipleArtists.value = t })

        repository.respArtistTopTracks.observe(lifecycleOwner,
            { t -> artistTopTracks.value = t })

        repository.respArtistAlbums.observe(lifecycleOwner,
            { t -> artistAlbums.value = t })

        repository.respRelatedArtists.observe(lifecycleOwner,
            { t -> relatedArtists.value = t })

        repository.respTrack.observe(lifecycleOwner,
            { t -> track.value = t })

        repository.respTrackAudioFeatures.observe(lifecycleOwner,
            { t -> trackAudioFeatures.value = t })

        repository.respAlbumTracks.observe(lifecycleOwner,
            { t -> albumTracks.value = t })

        repository.respAlbum.observe(lifecycleOwner,
            { t -> album.value = t })

        repository.respQueryResult.observe(lifecycleOwner,
            { t -> queryResults.value = t })

        repository.respSearchHistory.observe(lifecycleOwner,
            { t -> searchHistory.value = t })

        repository.respTrackFinderTracks.observe(lifecycleOwner,
            { t -> trackFinderTracks.value = t })

        repository.respAvailableDevices.observe(lifecycleOwner,
            { t -> availableDevices.value = t })

        repository.respGenres.observe(lifecycleOwner,
            { t -> genres.value = t })

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
    fun trackFinderGetAll(){
        repository.trackFinderGetAll()
    }
    fun trackFinderInsert(tracks: TrackFinderTracks){
        repository.trackFinderInsert(tracks)
    }
    fun trackFinderDeleteAll(){
        repository.trackFinderDeleteAll()
    }
    //retrofit methods
    fun getMyArtists(token: String,timeRange:String){
        repository.getMyFavArtists(token,timeRange)
    }
    fun getMyArtistsLimited(token: String, timeRange:String, limit: Int){
        repository.getMyFavArtistsLimited(token,timeRange,limit)
    }
    fun getMyTracks(token: String,timeRange:String){
        repository.getMyFavTracks(token,timeRange)
    }
    fun getMyTracksLimited(token: String,timeRange:String,limit: Int){
        repository.getMyFavTracksLimited(token,timeRange,limit)
    }
    fun getRecommendations(token: String,artistSeed:String,trackSeed:String) {
        repository.getRecommendations(token, artistSeed, trackSeed)
    }
    fun getRecommendedTrack(token: String,seedTrack:String,seedGenre:String,targetAcousticness:String,targetDanceability:String,targetEnergy:String,targetInstrumentalness:String,
                            targetLiveness:String, targetValence:String) {
        repository.getRecommendedTrack(token,seedTrack,seedGenre, targetAcousticness, targetDanceability, targetEnergy,
            targetInstrumentalness, targetLiveness, targetValence)
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
    fun getAvailableDevices(token: String){
        repository.getAvailableDevices(token)
    }
    fun getGenres(token: String){
        repository.getGenres(token)
    }


}