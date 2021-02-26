package com.uhi5d.spotibud.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uhi5d.spotibud.repository.Repository
import com.uhi5d.spotibud.TrackItems
import com.uhi5d.spotibud.Tracks
import com.uhi5d.spotibud.model.*

class MainViewModel(lifecycleOwner: LifecycleOwner,context: Context) : ViewModel() {
    var repository : Repository = Repository(context)

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
    fun getMyArtists(context: Context,token: String,timeRange:String){
        repository.getMyFavArtists(context, token,timeRange)
    }
    fun getMyArtistsLimited(context: Context,token: String, timeRange:String, limit: Int){
        repository.getMyFavArtistsLimited(context, token,timeRange,limit)
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