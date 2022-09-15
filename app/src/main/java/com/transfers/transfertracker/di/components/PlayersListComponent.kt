package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.AppModuleBinds
import com.transfers.transfertracker.di.modules.AppModuleProvides
import com.transfers.transfertracker.di.modules.NetworkModule
import com.transfers.transfertracker.view.country.CountryListViewModel
import com.transfers.transfertracker.view.player.PlayersListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleProvides::class,
    AppModuleBinds::class,
    NetworkModule::class,
])
interface PlayersListComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): PlayersListComponent
    }

    fun getPlayersListViewModel() : PlayersListViewModel
}