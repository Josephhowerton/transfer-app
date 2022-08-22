package com.transfers.transfertracker.model.squad

data class SquadResponse(
    val errors: Any,
    val paging: Paging,
    val response: List<Response>,
)