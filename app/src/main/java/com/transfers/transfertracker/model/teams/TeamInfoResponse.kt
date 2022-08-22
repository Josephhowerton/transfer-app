package com.transfers.transfertracker.model.teams

data class TeamInfoResponse(
    val errors: List<Any>,
    val paging: Paging,
    val response: List<Response>
)