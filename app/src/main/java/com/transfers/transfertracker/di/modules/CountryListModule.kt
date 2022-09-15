package com.transfers.transfertracker.di.modules

import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.view.country.CountryListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CountryListModule {

    @Provides
    @Singleton
    fun provideCountryListViewModel(repository: MainRepository, navigator: Navigator) : CountryListViewModel =
        CountryListViewModel(repository, navigator)
}