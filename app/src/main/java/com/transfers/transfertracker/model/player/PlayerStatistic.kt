package com.transfers.transfertracker.model.player

data class PlayerStatistic(
    val cards: Cards?,
    val dribbles: Dribbles?,
    val duels: Duels?,
    val fouls: Fouls?,
    val games: Games?,
    val goals: Goals?,
    val league: League?,
    val passes: Passes?,
    val penalty: Penalty?,
    val shots: Shots?,
    val substitutes: Substitutes?,
    val tackles: Tackles?,
    val team: Team?
)