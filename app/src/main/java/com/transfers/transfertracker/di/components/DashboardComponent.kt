package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.AppModuleBinds
import com.transfers.transfertracker.di.modules.AppModuleProvides
import com.transfers.transfertracker.di.modules.NetworkModule
import com.transfers.transfertracker.view.MainActivity
import com.transfers.transfertracker.view.dashboard.DashboardViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleProvides::class,
    AppModuleBinds::class,
    NetworkModule::class,
])
interface DashboardComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): DashboardComponent
    }

    fun getDashboardViewModel() : DashboardViewModel
}