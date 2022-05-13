package com.mbobiosio.firebaseremoteconfig.data.repository

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.mbobiosio.firebaseremoteconfig.BuildConfig
import com.mbobiosio.firebaseremoteconfig.data.model.RemoteConfigs
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 */
class RemoteConfigRepoImpl @Inject constructor() : RemoteConfigRepo {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    override fun initConfigs() {

        var cacheInterval = TimeUnit.HOURS.toSeconds(12)
        if (BuildConfig.DEBUG) cacheInterval = 0
        val configSettings = FirebaseRemoteConfigSettings.Builder().apply {
            minimumFetchIntervalInSeconds = cacheInterval
        }.build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        val defaults = hashMapOf<String, Any>().apply {
            "force_update" to false
            "message" to "Check update"
        }
        remoteConfig.setDefaultsAsync(defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                Timber.d("Successful ${it.result}")
            } else {
                Timber.d("Failed ${it.result}")
            }
        }
    }

    override fun getConfigs(): RemoteConfigs {
        return RemoteConfigs(
            forceUpdate = remoteConfig.getBoolean("force_update"),
            message = remoteConfig.getString("message")
        )
    }
}
