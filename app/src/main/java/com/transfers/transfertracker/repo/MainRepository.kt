package com.transfers.transfertracker.repo

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.stats.TeamStatistics
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun fetchAllCountries(): Single<List<Country>>
    fun fetchAllCountriesFromApi(): Single<List<Country>>
    fun fetchCountryByCode(code: String): Single<List<Country>>
    fun fetchCountryByName(name: String): Single<List<Country>>
    fun fetchCountriesBySearch(query: String): Observable<List<Country>>
    fun fetchLeagues() : Single<List<League>>
    fun fetchLeaguesByCountry(code: String) : Single<List<League>>
    fun fetchLatestTeamNews(q: String) : Single<List<News>>
    fun fetchLatestTransferNews(language: String) : Single<List<News>>
    fun fetchSquad(tid: String): Single<List<SquadPlayer>>
    fun fetchPlayerProfiles(leagueId: String, teamId: String, playerId: String): Single<List<PlayerProfile>>
    fun fetchTeams(id: Int) : Single<List<Team>>
    fun fetchTeamStatistics(leagueId: String, teamId: String) : Single<TeamStatistics>
    fun saveSelectedTeam(team: Team): Single<BaseResult<Boolean>>
    fun removeSelectedTeam(team: Team): Single<BaseResult<Boolean>>
    fun getCurrentTeam(): Single<Team>
    fun getAllUsersTeam() : Single<List<Team>>
    fun updateCurrentTeam(team: Team) : Single<BaseResult<Boolean>>
    fun getPlayerCountry(name: String) : Single<Country>
}