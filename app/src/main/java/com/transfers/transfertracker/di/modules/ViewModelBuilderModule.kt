package com.transfers.transfertracker.di.modules

import androidx.lifecycle.ViewModelProvider
import com.transfers.transfertracker.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {
    @Binds abstract fun bind(factory: ViewModelFactory): ViewModelProvider.Factory
}