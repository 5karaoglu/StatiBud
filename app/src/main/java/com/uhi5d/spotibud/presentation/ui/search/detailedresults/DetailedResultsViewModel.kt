package com.uhi5d.spotibud.presentation.ui.search.detailedresults

import androidx.lifecycle.*
import com.uhi5d.spotibud.data.local.datastore.DataStoreManager
import com.uhi5d.spotibud.domain.model.searchresults.SearchResults
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedResultsViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val useCase: UseCase
): ViewModel(){

    private val _searchResults: MutableLiveData<DataState<SearchResults>> = MutableLiveData()
    val searchResults : LiveData<DataState<SearchResults>> get() = _searchResults

    val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)

    @InternalCoroutinesApi
    fun search(token: String,str: String, type:String) = viewModelScope.launch {
        useCase.search(token,str,type,50).collect { state ->
            _searchResults.value = state
        }
    }
}