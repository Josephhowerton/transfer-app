package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.util.errors.ErrorTransformer
import com.transfers.transfertracker.model.news.NewsResponse
import com.transfers.transfertracker.network.NewsService
import com.transfers.transfertracker.source.NewsSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsSourceImpl @Inject constructor(
    private val service: NewsService,
    private val errorTransformer: ErrorTransformer
    ): NewsSource {

    override fun fetchLatestTeamNews(q: String, language: String) : Single<NewsResponse> =
        service.fetchLatestTeamNews(q, language)
            .subscribeOn(Schedulers.io())
            .doOnError { errorTransformer.convertThrowableForNewsData(it) }
            .observeOn(AndroidSchedulers.mainThread())


    override fun fetchLatestTransferNews(language: String) : Single<NewsResponse> =
        service.fetchLatestTransferNews(language)
            .subscribeOn(Schedulers.io())
            .doOnError { errorTransformer.convertThrowableForNewsData(it) }
            .observeOn(AndroidSchedulers.mainThread())
}