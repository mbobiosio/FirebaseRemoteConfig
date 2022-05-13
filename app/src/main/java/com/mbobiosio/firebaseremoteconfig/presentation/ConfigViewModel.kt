package com.mbobiosio.firebaseremoteconfig.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mbobiosio.firebaseremoteconfig.data.model.RemoteConfigs
import com.mbobiosio.firebaseremoteconfig.data.repository.RemoteConfigRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 */
@HiltViewModel
class ConfigViewModel @Inject constructor(remoteConfigRepo: RemoteConfigRepo) : ViewModel() {

    val remoteConfigLiveData = MutableLiveData<RemoteConfigs>()

    init {
        remoteConfigLiveData.value = remoteConfigRepo.getConfigs()
    }
}
