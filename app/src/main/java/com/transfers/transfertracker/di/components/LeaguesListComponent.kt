package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.AppModuleBinds
import com.transfers.transfertracker.di.modules.AppModuleProvides
import com.transfers.transfertracker.di.modules.NetworkModule
import com.transfers.transfertracker.view.league.LeaguesListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleProvides::class,
    AppModuleBinds::class,
    NetworkModule::class,
])
interface LeaguesListComponent {
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): LeaguesListComponent
    }

    fun getLeaguesListViewModel() : LeaguesListViewModel
}
