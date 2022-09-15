package com.transfers.transfertracker.network

import com.transfers.transfertracker.model.stats.StatisticsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface StatisticsService {

    @GET("/teams/statistics")
    fun fetchTeamsStatistics(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<StatisticsResponse>
}