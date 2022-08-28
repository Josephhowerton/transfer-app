package com.transfers.transfertracker.view.main.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.source.AuthSource
import com.transfers.transfertracker.util.result.BaseResult
import com.transfers.transfertracker.view.navigation.DashboardNavGraph
import com.transfers.transfertracker.view.navigation.Navigator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val repository: MainRepository,
                                             private val navigator: Navigator,
                                             private val authSource: AuthSource) : ViewModel() {

    private val _countriesList = mutableStateListOf<Country>()
    val countriesList: List<Country> get() = _countriesList

    private val _leaguesList = mutableStateListOf<League>()
    val leaguesList: List<League> get() = _leaguesList

    private val _teamsList = mutableStateListOf<Team>()
    val teamsList: List<Team> get() = _teamsList

    private val _usersTeams = mutableStateListOf<Team>()
    val usersTeams: List<Team> get() = _usersTeams

    init {
        getAllUsersTeams()
    }

    fun fetchCountries() {
        repository.fetchAllCountries()
            .subscribe(
                {
                    _countriesList.clear()
                    _countriesList.addAll(it)
                    navigator.navigateTo(Screen.COUNTRY_LIST)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun fetchLeaguesByCountry(country: Country) {
        repository.fetchLeaguesByCountry(country.code)
            .subscribe(
                {
                    _leaguesList.clear()
                    _leaguesList.addAll(it)
                    navigator.navigateTo(Screen.LEAGUE_LIST)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun fetchTeamsByLeague(league: League) {
        repository.fetchTeams(league.id)
            .subscribe(
                {
                    _teamsList.clear()
                    _teamsList.addAll(it)
                    navigator.navigateTo(Screen.TEAM_LIST)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
        )
    }

    fun getAllUsersTeams() {
        repository.getAllUsersTeam()
            .subscribe(
                {
                    _usersTeams.clear()
                    _usersTeams.addAll(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun saveSelectedTeam(team: Team) {
        repository.saveSelectedTeam(team)
            .subscribe(
                {
                    navigator.navigateTo(Screen.DASHBOARD)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun setTeamAsPrimary() {

    }

    fun fetchTeams(uid: String): List<Team> {
        TODO()
    }

    fun fetchPlayers(id: String, name: String) {

    }

    fun fetchStatistics(id: String, name: String) {

    }

    fun fetchTeamNews(id: String, name: String) {

    }

    fun signOut() {
        authSource.signOut()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ }, {})
    }
}