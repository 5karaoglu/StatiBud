package com.uhi5d.spotibud.presentation.ui.search

import androidx.lifecycle.*
import com.uhi5d.spotibud.data.local.datastore.DataStoreManager
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
    private val dataStoreManager: DataStoreManager,
    private val useCase: UseCase
): ViewModel(){

    private val _searchResults: MutableLiveData<DataState<SearchResults>> = MutableLiveData()
    val searchResults : LiveData<DataState<SearchResults>> get() = _searchResults

    private val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)
    @InternalCoroutinesApi
    fun search(str: String, type:String) = viewModelScope.launch {
        useCase.search(token.value!!,str,type,50).collect(object : FlowCollector<DataState<SearchResults>>{
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