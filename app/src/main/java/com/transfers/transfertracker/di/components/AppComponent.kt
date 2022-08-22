package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RepositoryModule::class,
    SubcomponentsModule::class,
    ViewModelBuilderModule::class,
    DataSourceModule::class,
    NetworkModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun authComponent(): AuthComponent.Factory
    fun dashboardComponent(): DashboardComponent.Factory

}