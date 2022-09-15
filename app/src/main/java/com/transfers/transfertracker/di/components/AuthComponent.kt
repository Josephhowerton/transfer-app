package com.transfers.transfertracker.di.components

import android.app.Application
import com.transfers.transfertracker.di.modules.AppModuleBinds
import com.transfers.transfertracker.di.modules.AppModuleProvides
import com.transfers.transfertracker.di.modules.NetworkModule
import com.transfers.transfertracker.di.modules.ViewModelModule
import com.transfers.transfertracker.view.auth.AuthActivity
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModuleProvides::class,
    AppModuleBinds::class,
    NetworkModule::class,
    ViewModelModule::class
])
interface AuthComponent {

    fun inject(activity: AuthActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application): AuthComponent
    }

    fun getAuthViewModel(): AuthViewModel
}