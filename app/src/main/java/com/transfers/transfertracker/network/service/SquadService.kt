package com.transfers.transfertracker.network.service

import com.transfers.transfertracker.model.player.PlayerResponse
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.model.tranfers.TransferResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface SquadService {

    @GET("/players/squads")
    fun fetchSquad(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<SquadResponse>

    @GET("/transfers")
    fun fetchTransfers(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<TransferResponse>

    @GET("/players")
    fun fetchPlayerProfile(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<PlayerResponse>
}