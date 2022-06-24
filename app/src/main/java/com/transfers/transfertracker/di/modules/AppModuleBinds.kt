package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.repo.impl.MainRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModuleBinds {
    @Binds abstract fun bindMainRepository(mainRepository: MainRepositoryImpl) : MainRepository
}