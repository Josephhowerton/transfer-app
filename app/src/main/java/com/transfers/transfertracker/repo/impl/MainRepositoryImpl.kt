package com.transfers.transfertracker.repo.impl

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.model.news.NewsResponse
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.source.*
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val countryDataSource: CountryDataSource,
    private val leaguesSource: LeaguesSource,
    private val newsSource: NewsSource,
    private val squadSource: SquadSource,
    private val teamsSource: TeamsSource,
    private val transferDataSource: TransferDataSource,
    private val userSource: UserSource,
): MainRepository {

    override fun fetchAllCountries(): Single<List<Country>> =
        countryDataSource.fetchAllCountries()

    override fun fetchCountryByCode(code: String): Single<List<Country>> =
        countryDataSource.fetchCountryByCode(code)

    override fun fetchCountryByName(name: String): Single<List<Country>> =
        countryDataSource.fetchCountryByName(name)

    override fun fetchCountriesBySearch(query: String): Observable<List<Country>> =
        countryDataSource.fetchCountriesBySearch(query)

    override fun fetchLeagues() : Single<List<League>> =
        leaguesSource.fetchLeagues()

    override fun fetchLeaguesByCountry(code: String): Single<List<League>> =
        leaguesSource.fetchLeaguesByCountry(code)

    override fun fetchLatestTeamNews(q: String, language: String) : Single<NewsResponse> =
        newsSource.fetchLatestTeamNews(q, language)

    override fun fetchLatestTransferNews(language: String) : Single<NewsResponse> =
        newsSource.fetchLatestTransferNews(language)

    override fun fetchSquad(tid: String): Single<List<Player>> =
        squadSource.fetchSquad(tid)

    override fun fetchSquadFromApi(tid: String): Single<SquadResponse> =
        squadSource.fetchSquadFromApi(tid)

    override fun fetchTeams(id: Int) : Single<List<Team>> =
        teamsSource.fetchTeams(id)

    override fun saveSelectedTeam(team: Team) : Single<BaseResult<Boolean>> =
        userSource.getUser()
            .flatMap{
                userSource.saveSelectedTeam(it, team)
            }

    override fun getAllUsersTeam() : Single<List<Team>> =
        userSource.getUser()
            .flatMap{
                userSource.getAllUsersTeam(it.id)
            }

    override fun getUserSelectedTeam(): Single<Team> = TODO()

    override fun updateSelectedTeam() = TODO()
}