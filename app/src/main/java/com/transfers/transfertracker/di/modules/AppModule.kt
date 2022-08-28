package com.transfers.transfertracker.di.modules

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transfers.transfertracker.util.errors.ErrorTransformer
import com.transfers.transfertracker.view.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideErrorTransformer() : ErrorTransformer = ErrorTransformer()

    @Provides
    @Singleton
    fun provideNavigator() : Navigator = Navigator()
}