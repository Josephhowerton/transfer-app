package com.transfers.transfertracker.di.components

import com.transfers.transfertracker.di.modules.ViewModelModule
import com.transfers.transfertracker.view.auth.AuthActivity
import com.transfers.transfertracker.view.main.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface AuthComponent {

    fun inject(activity: AuthActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : AuthComponent
    }
}