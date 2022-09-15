package com.transfers.transfertracker.view.country

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class CountryListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {

    private val _countriesList = mutableStateListOf<Country>()
    val countriesList: List<Country> get() = _countriesList

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        repository.fetchAllCountries()
            .filter {
                if(it.isEmpty()){
                    fetchCountriesFromApi()
                }
                it.isNotEmpty()
            }
            .subscribe(
                {
                    _countriesList.clear()
                    _countriesList.addAll(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    private fun fetchCountriesFromApi() {
        repository.fetchAllCountriesFromApi()
            .subscribe(
                {
                    _countriesList.clear()
                    _countriesList.addAll(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun navigateToLeaguesList(country: String){
        navigator.navigateTo(Screen.LEAGUE_LIST)
    }
}