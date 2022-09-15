package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.model.news.NewsResponse
import io.reactivex.rxjava3.core.Single

interface NewsSource {

    private companion object{
        const val DEFAULT_LANGUAGE = "en"
    }

    fun fetchLatestTeamNews(q: String, language: String = DEFAULT_LANGUAGE) : Single<List<News>>
    fun fetchLatestTransferNews(q: String, language: String = DEFAULT_LANGUAGE) : Single<List<News>>
}