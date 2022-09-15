package com.transfers.transfertracker.view.league

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class LeaguesListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {

    private val _leaguesList = mutableStateListOf<League>()
    val leaguesList: List<League> get() = _leaguesList

    fun fetchLeaguesByCountry(country: String) {
        repository.fetchLeaguesByCountry(country)
            .subscribe(
                {
                    _leaguesList.clear()
                    _leaguesList.addAll(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }
}