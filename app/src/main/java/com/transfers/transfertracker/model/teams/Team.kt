package com.transfers.transfertracker.model.teams

data class Team(
    val id: Int,
    var leagueId: Int?,
    val logo: String,
    val name: String,
    var code: String?,
    var country: String?,
    var founded: Int?,
    var national: Boolean?
)