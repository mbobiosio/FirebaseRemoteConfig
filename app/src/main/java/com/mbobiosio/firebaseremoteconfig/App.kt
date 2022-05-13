package com.mbobiosio.firebaseremoteconfig

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
