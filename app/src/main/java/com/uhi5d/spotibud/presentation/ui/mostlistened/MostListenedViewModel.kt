package com.uhi5d.spotibud.presentation.ui.mostlistened

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.domain.model.MyArtistsItem
import com.uhi5d.spotibud.domain.model.mytracks.MyTracksItem
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostListenedViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val useCase: UseCase,
    private val toastHelper: ToastHelper
): ViewModel(){

    private val _myTracks: MutableLiveData<MutableMap<String, List<MyTracksItem>>> = MutableLiveData()
    val myTracks get() = _myTracks

    private val _myArtists: MutableLiveData<MutableMap<String, List<MyArtistsItem>>> = MutableLiveData()
    val myArtists get() = _myArtists

    private fun getToken() = sharedPreferences.getString("token",null)

    fun getMyTracks(timeRange: String) = viewModelScope.launch {
        useCase.getMyTopTracks(getToken()!!,timeRange).collect { state ->
            when(state){
                is DataState.Success -> {
                    _myTracks.value!![timeRange] = state.data.items!!
                }
                DataState.Empty -> toastHelper.sendToast("You have no data in this" +
                        " section!")
                is DataState.Fail -> toastHelper.sendToast(state.e.localizedMessage!!)
                DataState.Loading -> TODO()
            }
        }
    }
    fun getMyArtists(timeRange: String) = viewModelScope.launch {
        useCase.getMyTopArtists(getToken()!!,timeRange).collect { state ->
            when(state){
                is DataState.Success -> {
                    _myArtists.value!![timeRange] = state.data.items!!
                }
                DataState.Empty -> toastHelper.sendToast("You have no data in this" +
                        " section!")
                is DataState.Fail -> toastHelper.sendToast(state.e.localizedMessage!!)
                DataState.Loading -> TODO()
            }
        }
    }
}