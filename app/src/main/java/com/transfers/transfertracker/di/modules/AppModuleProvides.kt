package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.model.errors.ErrorTransformer
import dagger.Module
import dagger.Provides

@Module
class AppModuleProvides {
    @Provides fun provideErrorTransformer() : ErrorTransformer = ErrorTransformer()
}