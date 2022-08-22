package com.transfers.transfertracker.model.league

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.teams.Team

data class Response(
    val country: Country,
    val league: League,
)