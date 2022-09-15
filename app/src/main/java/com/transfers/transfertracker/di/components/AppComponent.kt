package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleProvides::class,
    AppModuleBinds::class,
    NetworkModule::class,
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}