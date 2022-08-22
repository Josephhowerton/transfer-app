package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.news.NewsResponse
import io.reactivex.rxjava3.core.Single

interface NewsSource {
    fun fetchLatestTeamNews(q: String, language: String) : Single<NewsResponse>
    fun fetchLatestTransferNews(language: String) : Single<NewsResponse>
}