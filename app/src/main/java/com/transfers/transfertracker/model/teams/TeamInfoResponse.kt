package com.transfers.transfertracker.model.teams

data class TeamInfoResponse(
    val errors: List<Any>,
    val response: List<Club>
)