package com.uhi5d.statibud.presentation.ui.mostlistened

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uhi5d.statibud.application.ToastHelper
import com.uhi5d.statibud.data.local.datastore.DataStoreManager
import com.uhi5d.statibud.domain.model.MyArtists
import com.uhi5d.statibud.domain.model.mytracks.MyTracks
import com.uhi5d.statibud.domain.usecase.UseCase
import com.uhi5d.statibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostListenedViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val useCase: UseCase,
    private val toastHelper: ToastHelper
): ViewModel(){

    private val _myTracksShort: MutableLiveData<DataState<MyTracks>> = MutableLiveData()
    val myTracksShort get() = _myTracksShort

    private val _myTracksMedium: MutableLiveData<DataState<MyTracks>> = MutableLiveData()
    val myTracksMedium get() = _myTracksMedium

    private val _myTracksLong: MutableLiveData<DataState<MyTracks>> = MutableLiveData()
    val myTracksLong get() = _myTracksLong

    private val _myArtistsShort: MutableLiveData<DataState<MyArtists>> = MutableLiveData()
    val myArtistsShort get() = _myArtistsShort

    private val _myArtistsMedium: MutableLiveData<DataState<MyArtists>> = MutableLiveData()
    val myArtistsMedium get() = _myArtistsMedium

    private val _myArtistsLong: MutableLiveData<DataState<MyArtists>> = MutableLiveData()
    val myArtistsLong get() = _myArtistsLong

    val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)


    init {
        token.observeForever {
            if (it.length > 10){
                getMyTracksShort(it)
                getMyTracksMedium(it)
                getMyTracksLong(it)
                getMyArtistsShort(it)
                getMyArtistsMedium(it)
                getMyArtistsLong(it)
            }
        }
    }

    fun getMyTracksShort(token:String) = viewModelScope.launch {
        useCase.getMyTopTracks(token, timeRangeList[0]).collect { state ->
            _myTracksShort.value = state
        }
    }
    fun getMyTracksMedium(token:String) = viewModelScope.launch {
        useCase.getMyTopTracks(token, timeRangeList[1]).collect { state ->
            _myTracksMedium.value = state
        }
    }
    fun getMyTracksLong(token:String) = viewModelScope.launch {
        useCase.getMyTopTracks(token, timeRangeList[2]).collect { state ->
            _myTracksLong.value = state
        }
    }
    fun getMyArtistsShort(token: String) = viewModelScope.launch {
        useCase.getMyTopArtists(token, timeRangeList[0]).collect { state ->
            _myArtistsShort.value = state
        }
    }
    fun getMyArtistsMedium(token: String) = viewModelScope.launch {
        useCase.getMyTopArtists(token, timeRangeList[1]).collect { state ->
            _myArtistsMedium.value = state
        }
    }
    fun getMyArtistsLong(token: String) = viewModelScope.launch {
        useCase.getMyTopArtists(token, timeRangeList[2]).collect { state ->
            _myArtistsLong.value = state
        }
    }
}