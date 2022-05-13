package com.mbobiosio.firebaseremoteconfig.data.repository

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
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

    // Get remote config instance
    private val remoteConfig = Firebase.remoteConfig

    override fun initConfigs() {
        /**
         * [cacheInterval] defines the interval of fetches per hour.
         * Use [remoteConfigSettings] to set the minimum fetch interval
         * */
        var cacheInterval = TimeUnit.HOURS.toSeconds(12)
        if (BuildConfig.DEBUG) cacheInterval = 0
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = cacheInterval
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        // [END config settings]

        /*
        * Set the default parameters for Remote Config
        * Your app will use these default values until there's a change in the firebase console
        * */
        val defaults = hashMapOf<String, Any>().apply {
            "force_update" to false
            "message" to "Check update"
        }
        remoteConfig.setDefaultsAsync(defaults)
        // [END default config]

        /**
         * Fetch updates from Firebase console
         * */
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Timber.d("Successful ${it.result}")
                } else {
                    Timber.d("Failed ${it.result}")
                }
            }
        // [End fetch and activate]
    }

    /**
     * @return [RemoteConfigs] remote values
     * */
    override fun getConfigs(): RemoteConfigs {
        return RemoteConfigs(
            forceUpdate = remoteConfig.getBoolean("force_update"),
            message = remoteConfig.getString("message")
        )
    }
}
