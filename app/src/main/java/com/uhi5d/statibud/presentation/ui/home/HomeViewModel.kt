package com.uhi5d.statibud.presentation.ui.home

import androidx.lifecycle.*
import com.uhi5d.statibud.data.local.datastore.DataStoreManager
import com.uhi5d.statibud.domain.model.MyArtists
import com.uhi5d.statibud.domain.model.currentuser.CurrentUser
import com.uhi5d.statibud.domain.model.getArtistIds
import com.uhi5d.statibud.domain.model.mytracks.MyTracks
import com.uhi5d.statibud.domain.model.mytracks.getTrackIds
import com.uhi5d.statibud.domain.model.recenttracks.RecentTracks
import com.uhi5d.statibud.domain.model.recommendations.Recommendations
import com.uhi5d.statibud.domain.usecase.UseCase
import com.uhi5d.statibud.presentation.ui.mostlistened.TimeRange
import com.uhi5d.statibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val useCase: UseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _artists: MutableLiveData<DataState<MyArtists>> = MutableLiveData()
    val artists: LiveData<DataState<MyArtists>> get() = _artists

    private val _tracks: MutableLiveData<DataState<MyTracks>> = MutableLiveData()
    val tracks: LiveData<DataState<MyTracks>> get() = _tracks

    private val _recentTracks: MutableLiveData<DataState<RecentTracks>> = MutableLiveData()
    val recentTracks: LiveData<DataState<RecentTracks>> get() = _recentTracks

    private val _user: MutableLiveData<CurrentUser> = MutableLiveData()
    val user: LiveData<CurrentUser> get() = _user

    private val mediatorLiveData: MediatorLiveData<Pair<DataState<MyArtists>, DataState<MyTracks>>> =
        object : MediatorLiveData<Pair<DataState<MyArtists>, DataState<MyTracks>>>() {
            var myArtists: DataState<MyArtists>? = null
            var myTracks: DataState<MyTracks>? = null

            init {
                addSource(artists) { myArtists ->
                    this.myArtists = myArtists
                    myTracks?.let { value = myArtists to it }
                }
                addSource(tracks) { myTracks ->
                    this.myTracks = myTracks
                    myArtists?.let { value = it to myTracks }
                }
            }
        }

    private val _recommendations: MutableLiveData<DataState<Recommendations>> = MutableLiveData()
    val recommendations: LiveData<DataState<Recommendations>> get() = _recommendations

    val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)


    fun getRecommendations(token: String) {
        getMyTopArtists(token)
        getMyTopTracks(token)

        mediatorLiveData.observeForever { pair ->
            if (pair.first is DataState.Success && pair.second is DataState.Success) {
                viewModelScope.launch {
                    useCase.getRecommendations(
                        token,
                        (pair.first as DataState.Success<MyArtists>).data.getArtistIds(),
                        (pair.second as DataState.Success<MyTracks>).data.getTrackIds()
                    )
                        .collect {
                            _recommendations.value = it
                        }
                }
            }
        }
    }

    fun getRecentTracks(token: String) = viewModelScope.launch {
        useCase.getMyRecentPlayed(token).collect { state ->
            _recentTracks.value = state
        }
    }


    private fun getMyTopArtists(token: String) = viewModelScope.launch {
        useCase.getMyTopArtists(token, TimeRange.SHORT.str, 2)
            .collect { state ->
                _artists.value = state
            }
    }

    private fun getMyTopTracks(token: String) = viewModelScope.launch {
        useCase.getMyTopTracks(token, TimeRange.SHORT.str, 2)
            .collect { state ->
                _tracks.value = state
            }
    }

    fun getUserDisplayName(token: String) = viewModelScope.launch {
        useCase.getMyProfile(token).collect {
            if (it is DataState.Success) {
                _user.value = it.data
            }
        }
    }
}