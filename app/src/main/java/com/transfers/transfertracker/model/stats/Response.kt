package com.transfers.transfertracker.model.stats

import com.transfers.transfertracker.model.teams.Team

data class Response(
    val biggest: Biggest,
    val cards: Cards,
    val clean_sheet: CleanSheet,
    val failed_to_score: FailedToScore,
    val fixtures: Fixtures,
    val form: String,
    val goals: GoalsX,
    val league: League,
    val lineups: List<Lineup>,
    val penalty: Penalty,
    val team: Team
)