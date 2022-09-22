package com.transfers.transfertracker.network.service

import com.transfers.transfertracker.model.teams.TeamInfoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface TeamService {

    @GET("/teams")
    fun fetchTeams(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<TeamInfoResponse>
}