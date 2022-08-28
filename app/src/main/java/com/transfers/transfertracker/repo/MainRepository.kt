package com.transfers.transfertracker.repo

import com.transfers.transfertracker.model.User
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.model.news.NewsResponse
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MainRepository {
    fun fetchAllCountries(): Single<List<Country>>
    fun fetchCountryByCode(code: String): Single<List<Country>>
    fun fetchCountryByName(name: String): Single<List<Country>>
    fun fetchCountriesBySearch(query: String): Observable<List<Country>>
    fun fetchLeagues() : Single<List<League>>
    fun fetchLeaguesByCountry(code: String) : Single<List<League>>
    fun fetchLatestTeamNews(q: String, language: String) : Single<NewsResponse>
    fun fetchLatestTransferNews(language: String) : Single<NewsResponse>
    fun fetchSquad(tid: String): Single<List<Player>>
    fun fetchSquadFromApi(tid: String): Single<SquadResponse>
    fun fetchTeams(id: Int) : Single<List<Team>>
    fun saveSelectedTeam(team: Team): Single<BaseResult<Boolean>>
    fun getUserSelectedTeam(): Single<Team>
    fun getAllUsersTeam() : Single<List<Team>>
    fun updateSelectedTeam()
}