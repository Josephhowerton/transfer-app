package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.*
import com.transfers.transfertracker.view.country.CountryListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CountryListModule::class,
    AppModuleProvides::class,
    AppModuleBinds::class,
    NetworkModule::class,
])
interface CountryListComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): CountryListComponent
    }

    fun getCountListViewModel() : CountryListViewModel
}