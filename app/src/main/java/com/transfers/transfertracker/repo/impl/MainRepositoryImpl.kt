package com.transfers.transfertracker.repo.impl

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.stats.TeamStatistics
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.model.tranfers.PlayerTransfer
import com.transfers.transfertracker.model.tranfers.Transfer
import com.transfers.transfertracker.model.tranfers.TransferData
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.source.*
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val countryDataSource: CountryDataSource,
    private val leaguesSource: LeaguesSource,
    private val newsSource: NewsSource,
    private val squadSource: SquadSource,
    private val teamsSource: TeamsSource,
    private val userSource: UserSource,
    private val statisticsSource: StatisticsSource
): MainRepository {


    init {
        countryDataSource.getAllCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    if (it.isEmpty()){
                        fetchAllCountries()
                    }
                },
                {
                    fetchAllCountries()
                }
            )
    }

    override fun getAllCountries(): Single<List<Country>> =
        countryDataSource.getAllCountries()

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

    override fun fetchLatestTeamNews(q: String) : Single<List<News>> =
        newsSource.fetchLatestTeamNews(q)

    override fun fetchLatestTransferNews(language: String) : Single<List<News>> =
        newsSource.fetchLatestTransferNews(language)

    override fun fetchSquad(tid: String): Single<List<SquadPlayer>> =
        squadSource.fetchSquad(tid)

    override fun fetchTransfers(tid: String): Single<List<PlayerTransfer>>  =
        squadSource.fetchTransfers(tid)

    override fun fetchPlayerProfiles(leagueId: String, teamId: String): Single<List<PlayerProfile>> =
        squadSource.fetchPlayerProfiles(leagueId, teamId)

    override fun fetchPlayerProfile(leagueId: String, teamId: String, playerId: String): Single<PlayerProfile> =
        squadSource.fetchPlayerProfile(leagueId, teamId, playerId)

    override fun fetchPlayerProfile(teamId: String, playerId: String): Single<PlayerProfile> =
        squadSource.fetchPlayerProfile(teamId, playerId)

    override fun fetchTeams(id: String) : Single<List<Team>> =
        teamsSource.fetchTeams(id)

    override fun fetchTeamStatistics(leagueId: String, teamId: String): Single<TeamStatistics> =
        statisticsSource.fetchTeamsStatistics(leagueId, teamId)

    override fun saveSelectedTeam(team: Team) : Single<BaseResult<Boolean>> =
        userSource.getUser()
            .concatMap {
                it.currentTeam = team.id.toString()
                userSource.updateUser(it)
            }
            .flatMap {
                userSource.saveSelectedTeam(it, team)
            }
            .doOnSuccess {
                updateCurrentTeam(team)
            }

    override fun removeSelectedTeam(team: Team): Single<BaseResult<Boolean>> =
        userSource.getUser()
            .flatMap {
                userSource.removeSelectedTeam(user = it, team = team)
            }

    override fun getAllUsersTeam() : Single<List<Team>> =
        userSource.getUser()
            .flatMap{
                userSource.getAllUsersTeam(it.id)
            }

    override fun getCurrentTeam(): Single<Team> =
        userSource.getUser()
            .flatMap {
                userSource.getCurrentTeam(it)
            }


    override fun updateCurrentTeam(team: Team) : Single<BaseResult<Boolean>> =
        userSource.getUser()
            .flatMap {
                it.currentTeam = team.id.toString()
                userSource.updateUserTeam(user = it)
            }

    override fun getPlayerCountry(name: String) : Single<Country> =
        countryDataSource.getPlayerCountry(name)
}