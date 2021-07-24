package com.uhi5d.spotibud.presentation.ui.trackrecommendation

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.data.local.datastore.DataStoreManager
import com.uhi5d.spotibud.domain.model.genres.Genres
import com.uhi5d.spotibud.domain.model.searchresults.SearchResultsTracksItem
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.presentation.ui.search.DataType
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationsViewModel
    @Inject constructor(
        private val dataStoreManager: DataStoreManager,
        private val useCase: UseCase,
        private val toastHelper: ToastHelper
    ): ViewModel() {

    private val _searchResults : MutableLiveData<List<SearchResultsTracksItem>> = MutableLiveData()
    val searchResults get() = _searchResults

    private val _genres : MutableLiveData<DataState<Genres>> = MutableLiveData()
    val genres get() = _genres

        private val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)
        fun search(query: String) = viewModelScope.launch {
            useCase.search(token.value!!,query,DataType.TRACKS.name,10).collect { state ->
                when(state){
                    is DataState.Success -> {
                        _searchResults.value = state.data.tracks?.items
                    }
                    DataState.Empty -> toastHelper.sendToast(
                        Resources.getSystem().getString(R.string.search_result_empty))
                    is DataState.Fail -> toastHelper.sendToast(
                        Resources.getSystem().getString(R.string.search_result_fail) +
                        state.e.localizedMessage)
                    DataState.Loading -> {}
                }
            }
        }

    fun getGenres() = viewModelScope.launch {
        useCase.getGenres(token.value!!).collect { state ->
            _genres.value = state
        }
    }

}