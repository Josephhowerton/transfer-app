package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.network.LeagueEndpoints.DEFAULT_VALUE_CURRENT
import com.transfers.transfertracker.network.LeagueEndpoints.DEFAULT_VALUE_TYPE
import com.transfers.transfertracker.network.LeagueEndpoints.KEY_COUNTRY_CODE
import com.transfers.transfertracker.network.LeagueEndpoints.KEY_CURRENT
import com.transfers.transfertracker.network.LeagueEndpoints.KEY_TYPE
import com.transfers.transfertracker.network.LeagueService
import com.transfers.transfertracker.source.LeaguesSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LeagueDataSourceImpl @Inject constructor(private val leaguesService: LeagueService) : LeaguesSource {

    private val leaguesMap: MutableMap<String, String> = mutableMapOf()

    init {
        leaguesMap[KEY_CURRENT] = DEFAULT_VALUE_CURRENT
        leaguesMap[KEY_TYPE]= DEFAULT_VALUE_TYPE
    }

    override fun fetchLeagues() : Single<List<League>> =
        leaguesService.fetchLeaguesRemote(API_FOOTBALL_HEADER_MAP, leaguesMap)
            .map { data ->
                val leagues = mutableListOf<League>()
                for(response in data.response){
                    val league = response.league
                    val country = response.country
                    league.countryCode = country.code
                    league.country = country.name
                    league.flag = country.flag
                    leagues.add(league)
                }
                return@map leagues
            }


    override fun fetchLeaguesByCountry(code: String) : Single<List<League>> {
        leaguesMap[KEY_COUNTRY_CODE] = code
        return leaguesService.fetchLeaguesRemote(API_FOOTBALL_HEADER_MAP, leaguesMap)
            .map { data ->
                val leagues = mutableListOf<League>()
                for (response in data.response) {
                    val league = response.league
                    val country = response.country
                    league.countryCode = country.code
                    league.country = country.name
                    league.flag = country.flag
                    leagues.add(league)
                }
                return@map leagues
            }
    }
}