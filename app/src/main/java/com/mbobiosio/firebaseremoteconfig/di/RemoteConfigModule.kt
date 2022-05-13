package com.mbobiosio.firebaseremoteconfig.di

import com.mbobiosio.firebaseremoteconfig.data.repository.RemoteConfigRepo
import com.mbobiosio.firebaseremoteconfig.data.repository.RemoteConfigRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author Mbuodile Obiosio
 * Twitter: @cazewonder
 */

@Module
@InstallIn(SingletonComponent::class)
class RemoteConfigModule {

    @Provides
    @Singleton
    fun bindConfig(remoteConfigRepo: RemoteConfigRepoImpl): RemoteConfigRepo {
        remoteConfigRepo.initConfigs()
        return RemoteConfigRepoImpl()
    }
}
