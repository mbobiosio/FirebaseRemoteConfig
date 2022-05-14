package com.mbobiosio.firebaseremoteconfig.data.repository

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mbobiosio.firebaseremoteconfig.BuildConfig
import com.mbobiosio.firebaseremoteconfig.data.model.RemoteConfigs
import com.mbobiosio.firebaseremoteconfig.util.DefaultConfigs
import timber.log.Timber
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
        val cacheInterval = 3000L // 3000 milliseconds Long equivalent of 3 seconds
        val minFetchInterval: Long = if (BuildConfig.DEBUG) {
            0
        } else {
            cacheInterval
        }
        val configSettings = remoteConfigSettings {
            fetchTimeoutInSeconds = 20L
            minimumFetchIntervalInSeconds = minFetchInterval
        }
        // [END config settings]

        /*
        * Set the default parameters for Remote Config
        * Your app will use these default values until there's a change in the firebase console
        * */
        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(DefaultConfigs.getDefaultParams())
        }
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
            }.addOnFailureListener {
                Timber.d("Exception ${it.message}")
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
