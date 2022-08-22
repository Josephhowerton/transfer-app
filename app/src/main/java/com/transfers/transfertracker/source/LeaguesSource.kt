package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.league.League
import io.reactivex.rxjava3.core.Single

interface LeaguesSource {
    fun fetchLeagues() : Single<List<League>>
    fun fetchLeaguesByCountry(code: String) : Single<List<League>>
}