package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.stats.TeamStatistics
import io.reactivex.rxjava3.core.Single

interface StatisticsSource {
    fun fetchTeamsStatistics(leagueId: String, teamId: String) : Single<TeamStatistics>
}