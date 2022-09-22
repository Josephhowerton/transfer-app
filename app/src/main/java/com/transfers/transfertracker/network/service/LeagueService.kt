package com.transfers.transfertracker.network.service

import com.transfers.transfertracker.model.league.LeagueResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface LeagueService {

    @GET("/leagues")
    fun fetchLeaguesRemote(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<LeagueResponse>
}