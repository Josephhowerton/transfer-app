package com.transfers.transfertracker.model.teams

data class Team(
    var id: Int? = -1,
    var leagueId: Int? = -1,
    var logo: String? = "",
    var name: String? = "",
    var code: String? = "",
    var country: String? = "",
    var founded: Int? = -1,
    var national: Boolean? = false
)