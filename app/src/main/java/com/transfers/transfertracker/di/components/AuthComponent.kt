package com.transfers.transfertracker.di.components

import com.transfers.transfertracker.di.modules.AuthModule
import com.transfers.transfertracker.view.auth.AuthActivity
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    fun inject(activity: AuthActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : AuthComponent
    }


}