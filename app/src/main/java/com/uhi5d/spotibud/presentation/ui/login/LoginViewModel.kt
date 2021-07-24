package com.uhi5d.spotibud.presentation.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.uhi5d.spotibud.data.local.datastore.DataStoreManager
import com.uhi5d.spotibud.domain.model.accesstoken.AccessToken
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val useCase: UseCase
): ViewModel(){

    private val _token: MutableLiveData<DataState<AccessToken>> = MutableLiveData()
    val token get() = _token

    val t = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)

    val codeVerifier = dataStoreManager.getCodeVerifier.asLiveData(viewModelScope.coroutineContext)
    val codeChallenge = dataStoreManager.getCodeChallenge.asLiveData(viewModelScope.coroutineContext)

    fun setCodeVerifier(cv: String) = viewModelScope.launch { dataStoreManager.saveCodeVerifier(cv) }
    fun setCodeChallenge(cc: String) = viewModelScope.launch { dataStoreManager.saveCodeChallenge(cc) }

    fun getToken(
        url:String,
        clientId: String,
        grantType: String,
        code: String,
        redirectUri: String,
        codeVerifier: String
    ) = viewModelScope.launch {
        useCase.getToken(url,clientId, grantType, code, redirectUri, codeVerifier).collect { state ->
            _token.value = state
            when(state){
                is DataState.Success -> dataStoreManager.saveToken(state.data.accessToken)
            }
        }
    }
}