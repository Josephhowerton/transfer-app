package com.transfers.transfertracker.di.modules

import com.transfers.source.MainRepository
import com.transfers.source.UserSource
import com.transfers.source.impl.MainRepositoryImpl
import com.transfers.source.impl.RemoteUserSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds abstract fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl) : MainRepository
    @Binds abstract fun bindRemoteSource(remoteUserSourceImpl: RemoteUserSourceImpl) : UserSource

}