package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.AppModule
import com.transfers.transfertracker.di.modules.SubcomponentsModule
import com.transfers.transfertracker.di.modules.ViewModelBuilderModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    SubcomponentsModule::class,
    ViewModelBuilderModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun authComponent(): AuthComponent.Factory


}