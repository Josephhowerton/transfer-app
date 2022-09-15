package com.transfers.transfertracker.di.modules

import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.di.annotations.ViewModelKey
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}