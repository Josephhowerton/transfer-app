package com.transfers.transfertracker.network

import com.transfers.transfertracker.model.squad.SquadResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface SquadService {

    @GET("/players/squads")
    fun fetchSquad(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<SquadResponse>

}