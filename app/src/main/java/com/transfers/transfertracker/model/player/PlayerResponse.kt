package com.transfers.transfertracker.model.player

data class PlayerResponse(
    val errors: Any,
    val response: List<PlayerProfile>?,
)