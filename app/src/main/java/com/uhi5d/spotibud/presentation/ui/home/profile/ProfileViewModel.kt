package com.uhi5d.spotibud.presentation.ui.home.profile

import androidx.lifecycle.*
import com.uhi5d.spotibud.application.ToastHelper
import com.uhi5d.spotibud.data.local.datastore.DataStoreManager
import com.uhi5d.spotibud.domain.model.currentuser.CurrentUser
import com.uhi5d.spotibud.domain.model.devices.Devices
import com.uhi5d.spotibud.domain.usecase.UseCase
import com.uhi5d.spotibud.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val useCase: UseCase,
    private val toastHelper: ToastHelper
): ViewModel(){

    private val _profileInfo: MutableLiveData<DataState<CurrentUser>> = MutableLiveData()
    val profileInfo: LiveData<DataState<CurrentUser>> get() = _profileInfo

    private val _devices: MutableLiveData<DataState<Devices>> = MutableLiveData()
    val devices: LiveData<DataState<Devices>> get() = _devices

    private val token = dataStoreManager.getToken.asLiveData(viewModelScope.coroutineContext)

    fun getProfileInfo() = viewModelScope.launch {
        useCase.getMyProfile(token.value!!).collect { state ->
            when(state){
                is DataState.Success -> {}
                DataState.Empty -> TODO()
                is DataState.Fail -> {
                    toastHelper.sendToast(state.e.localizedMessage!!)
                }
                DataState.Loading -> TODO()
            }
            _profileInfo.value = state
        }
    }

    fun getAvailableDevices() = viewModelScope.launch {
        useCase.getAvailableDevices(token.value!!).collect {
            _devices.value = it
        }
    }
}