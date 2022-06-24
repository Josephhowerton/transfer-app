package com.transfers.transfertracker.di.modules

import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.di.annotations.ViewModelKey
import com.transfers.transfertracker.repo.AuthRepository
import com.transfers.transfertracker.repo.impl.AuthRepositoryImpl
import com.transfers.transfertracker.source.AuthSource
import com.transfers.transfertracker.source.impl.AuthSourceImpl
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds abstract fun bindAuthSource(authSource: AuthSourceImpl) : AuthSource
    @Binds abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository
}