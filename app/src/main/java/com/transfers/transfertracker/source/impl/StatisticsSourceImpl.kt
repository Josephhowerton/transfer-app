package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.model.stats.TeamStatistics
import com.transfers.transfertracker.network.StatisticsService
import com.transfers.transfertracker.source.StatisticsSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StatisticsSourceImpl @Inject constructor(private val statisticsService: StatisticsService): StatisticsSource{
    private var statisticsMap: MutableMap<String, String> = mutableMapOf()

    private companion object{
        const val KEY_LEAGUE_ID = "league"
        const val KEY_TEAM_ID = "team"
        const val KEY_SEASON = "season"
    }

    override fun fetchTeamsStatistics(leagueId: String, teamId: String): Single<TeamStatistics> {
        statisticsMap = mutableMapOf(
            KEY_LEAGUE_ID to leagueId,
            KEY_TEAM_ID to teamId,
            KEY_SEASON to "2022"
        )
        return statisticsService.fetchTeamsStatistics(API_FOOTBALL_HEADER_MAP, statisticsMap)
            .map {
                it.response
            }
    }
}