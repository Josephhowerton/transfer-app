package com.transfers.transfertracker.model.squad

import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.teams.Team

data class Response(
    val players: List<Player>,
    val team: Team
)