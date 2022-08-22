package com.transfers.transfertracker.di.modules

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transfers.transfertracker.util.errors.ErrorTransformer
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides fun provideErrorTransformer() : ErrorTransformer = ErrorTransformer()
}