package com.mbobiosio.firebaseremoteconfig.data.repository

import com.mbobiosio.firebaseremoteconfig.data.model.RemoteConfigs

/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 * Remote Configuration Repository interface
 */
interface RemoteConfigRepo {

    fun initConfigs()

    fun getConfigs(): RemoteConfigs
}
