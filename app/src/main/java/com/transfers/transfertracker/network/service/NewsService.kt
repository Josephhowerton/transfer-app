package com.transfers.transfertracker.network.service

import com.transfers.transfertracker.model.news.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface NewsService {

    @GET("news")
    fun fetchLatestTeamNews(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<NewsResponse>

    @GET("news")
    fun fetchLatestTransferNews(@HeaderMap headers: Map<String, String>, @QueryMap params: Map<String, String>) : Single<NewsResponse>
}