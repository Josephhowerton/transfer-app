package com.transfers.transfertracker.model.league

data class LeagueResponse(
    val errors: List<Any>,
    val paging: Paging,
    val response: List<Response>,
)