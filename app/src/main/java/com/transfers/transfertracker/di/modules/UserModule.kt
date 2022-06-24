package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.source.UserSource
import com.transfers.transfertracker.source.impl.UserSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UserModule {
    @Binds
    abstract fun bindUserSource(authSource: UserSourceImpl) : UserSource
}