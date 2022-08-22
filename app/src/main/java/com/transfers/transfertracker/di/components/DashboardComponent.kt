package com.transfers.transfertracker.di.components

import com.transfers.transfertracker.di.modules.ViewModelModule
import com.transfers.transfertracker.view.main.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface DashboardComponent {
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory{
        fun create() : DashboardComponent
    }
}