package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.repo.AuthRepository
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.repo.impl.AuthRepositoryImpl
import com.transfers.transfertracker.repo.impl.MainRepositoryImpl
import com.transfers.transfertracker.source.AuthSource
import com.transfers.transfertracker.source.impl.AuthSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds abstract fun bindMainRepository(mainRepository: MainRepositoryImpl) : MainRepository
    @Binds abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository
}