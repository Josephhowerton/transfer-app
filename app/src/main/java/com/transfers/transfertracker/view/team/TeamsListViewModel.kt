package com.transfers.transfertracker.view.team

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class TeamsListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {

    private val _teamsList = mutableStateListOf<Team>()
    val teamsList: List<Team> get() = _teamsList

    fun fetchTeamsByLeague(league: League) {
        league.id?.let { id ->
            repository.fetchTeams(id)
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
}