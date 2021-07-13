package com.uhi5d.spotibud.presentation.ui.search

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val useCase: UseCase
) :ViewModel(){

    private val _searchResults : MutableLiveData<DataState<SearchResults>> = MutableLiveData()
    val searchResults : LiveData<DataState<SearchResults>> get() = _searchResults

    fun getToken() = sharedPreferences.getString("token",null)

    @InternalCoroutinesApi
    fun search(query: String) = viewModelScope.launch {
        useCase.search(getToken()!!,query,limit = 3).collect(object : FlowCollector<DataState<SearchResults>>{
            override suspend fun emit(value: DataState<SearchResults>) {
                when(value){
                    is DataState.Success -> {_searchResults.value = value}
                    DataState.Empty -> _searchResults.value = value
                    is DataState.Fail -> _searchResults.value = value
                    DataState.Loading -> _searchResults.value = value
                }
            }
        })
    }
}