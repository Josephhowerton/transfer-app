package com.transfers.transfertracker.di.modules

import android.app.Application
import androidx.room.Room
import com.transfers.transfertracker.persistents.LocalCache
import com.transfers.transfertracker.persistents.dao.CountryDAO
import com.transfers.transfertracker.util.errors.ErrorTransformer
import com.transfers.transfertracker.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleProvides {
    private const val DATA_BASE_NAME = "local-cache"

    @Provides
    @Singleton
    fun provideLocalCache(application: Application): LocalCache =
        Room.databaseBuilder(application, LocalCache::class.java, DATA_BASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCountryDao(localCache: LocalCache): CountryDAO = localCache.getCountryDAO()

    @Provides
    @Singleton
    fun provideErrorTransformer() : ErrorTransformer = ErrorTransformer()

    @Provides
    @Singleton
    fun provideNavigator() : Navigator = Navigator()
}