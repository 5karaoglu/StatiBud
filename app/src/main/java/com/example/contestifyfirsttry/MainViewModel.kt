package com.example.contestifyfirsttry

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contestifyfirsttry.model.User
import com.spotify.android.appremote.api.SpotifyAppRemote
import kotlinx.android.synthetic.main.activity_main.*

class MainViewModel(lifecycleOwner: LifecycleOwner) : ViewModel() {
    var artistsList = MutableLiveData<Artists>()
    var tracksList = MutableLiveData<Tracks>()
    var repository : Repository = Repository()
    var user = MutableLiveData<User>()


    init {
        repository.respArtists.observe(lifecycleOwner,object : Observer<Artists>{
            override fun onChanged(t: Artists?) {
                artistsList.value = t
            }
        })
        repository.respTracks.observe(lifecycleOwner,object : Observer<Tracks>{
            override fun onChanged(t: Tracks?) {
                tracksList.value = t
            }
        })
        repository.respUser.observe(lifecycleOwner,object : Observer<User>{
            override fun onChanged(t: User?) {
                user.value = t
            }
        })
    }

    fun getMyArtists(token: String){
        repository.getMyFavArtists(token)
    }
    fun getMyTracks(token: String){
        repository.getMyFavTracks(token)
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

}