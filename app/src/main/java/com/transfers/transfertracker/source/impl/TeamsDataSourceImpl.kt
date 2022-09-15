package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.network.TeamService
import com.transfers.transfertracker.network.TeamsEndpoints
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_SEASON
import com.transfers.transfertracker.source.TeamsSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TeamsDataSourceImpl @Inject constructor(private val teamsService: TeamService) : TeamsSource {

    private val teamsMap: MutableMap<String, String> = mutableMapOf()

    init {
        teamsMap[KEY_SEASON]= TeamsEndpoints.DEFAULT_VALUE_SEASON
    }

    override fun fetchTeams(id: Int) : Single<List<Team>> =
        teamsService.fetchTeams(API_FOOTBALL_HEADER_MAP, setLeagueId(id))
            .map { data ->
                val teams = mutableListOf<Team>()
                for (response in data.response) {
                    val team = response.team
                    if(team != null){
                        team.leagueId = id
                        teams.add(team)
                    }
                    else {
                        throw Exception("error parsing teams list")
                    }
                }
                return@map teams
            }

    private fun setLeagueId(id: Int) : MutableMap<String, String>{
        teamsMap[TeamsEndpoints.KEY_LEAGUE] = id.toString()
        return teamsMap
    }
}