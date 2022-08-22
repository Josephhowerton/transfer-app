package com.transfers.transfertracker.network

import com.transfers.transfertracker.BuildConfig
import com.transfers.transfertracker.model.news.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    private companion object {
        const val KEY = "apikey"
        const val CATEGORY = "category"
        const val QUERY = "q"
        const val LANGUAGE = "language"

        const val SPORTS = "sports"
        const val TRANSFERS = "transfers"
    }

    @GET("1/news?${KEY}=${BuildConfig.NEWS_DATA_KEY}&${CATEGORY}=${SPORTS}&${QUERY}={query}&${LANGUAGE}=language")
    fun fetchLatestTeamNews(@Path("query") q: String, @Path("language") language: String) : Single<NewsResponse>

    @GET("1/news?${KEY}=${BuildConfig.NEWS_DATA_KEY}&${CATEGORY}=${SPORTS}&${QUERY}=${TRANSFERS}&${LANGUAGE}=language")
    fun fetchLatestTransferNews(@Path("language") language: String) : Single<NewsResponse>
}