package com.transfers.transfertracker.model.squad

import com.transfers.transfertracker.model.teams.Team

data class Response(
    val players: List<SquadPlayer>?,
    val team: Team?
)