package com.uhi5d.spotibud.search

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
class DetailedResultsViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val useCase: UseCase
): ViewModel(){

    private val _searchResults: MutableLiveData<DataState<SearchResults>> = MutableLiveData()
    val searchResults : LiveData<DataState<SearchResults>> get() = _searchResults

    private fun getToken() = sharedPreferences.getString("token",null)

    @InternalCoroutinesApi
    fun search(str: String, type:String) = viewModelScope.launch {
        useCase.search(getToken()!!,str,type,50).collect(object : FlowCollector<DataState<SearchResults>>{
            override suspend fun emit(value: DataState<SearchResults>) {
                when(value){
                    is DataState.Success -> {}
                    DataState.Empty -> TODO()
                    is DataState.Fail -> TODO()
                    DataState.Loading -> TODO()
                }
                _searchResults.value = value
            }

        })
    }
}