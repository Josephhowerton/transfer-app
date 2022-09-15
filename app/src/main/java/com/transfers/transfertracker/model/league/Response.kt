package com.transfers.transfertracker.model.league

import com.transfers.transfertracker.model.country.Country

data class Response(
    val country: Country?,
    val league: League?,
)