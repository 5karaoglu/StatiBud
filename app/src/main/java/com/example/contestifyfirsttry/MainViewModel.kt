package com.example.contestifyfirsttry

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.model.Item
import com.example.contestifyfirsttry.model.RecentTracks
import com.example.contestifyfirsttry.model.User
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.android.synthetic.main.activity_main.*

class MainViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {
    var artistsListShortTerm = MutableLiveData<Artists>()
    var artistsListMidTerm = MutableLiveData<Artists>()
    var artistsListLongTerm = MutableLiveData<Artists>()

    var tracksListShortTerm = MutableLiveData<Tracks>()
    var tracksListMidTerm = MutableLiveData<Tracks>()
    var tracksListLongTerm = MutableLiveData<Tracks>()

    var repository : Repository = Repository()
    var recentTracks = MutableLiveData<RecentTracks>()
    var user = MutableLiveData<User>()

    var currentArtist = MutableLiveData<Item>()


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
    fun setCurrentArtist(artist:Item){
        currentArtist.value = artist
    }

}