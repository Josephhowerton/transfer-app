package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.util.errors.ErrorTransformer
import com.transfers.transfertracker.network.service.NewsService
import com.transfers.transfertracker.source.NewsSource
import com.transfers.transfertracker.util.Keys.MAP_HEADERS_NEWS
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NewsSourceImpl @Inject constructor(
    private val service: NewsService,
    private val errorTransformer: ErrorTransformer
    ): NewsSource {

    private var queryMap: MutableMap<String, String> = mutableMapOf()

    private companion object {
        const val KEY_CATEGORY = "category"
        const val PARAM_CATEGORY = "sports"
        const val KEY_QUERY = "q"
        const val KEY_LANGUAGE = "language"
        const val TRANSFERS = "transfers"
    }

    override fun fetchLatestTeamNews(q: String, language: String) : Single<List<News>> {
        queryMap = mutableMapOf (
            KEY_CATEGORY to PARAM_CATEGORY,
            KEY_QUERY to q,
            KEY_LANGUAGE to language
        )
        return service.fetchLatestTeamNews(MAP_HEADERS_NEWS, queryMap)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.results?.let {
                    return@map it
                }
                throw Exception("List is null")
            }
            .doOnError { errorTransformer.convertThrowableForNewsData(it) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchLatestTransferNews(q: String, language: String) : Single<List<News>> {
        queryMap = mutableMapOf (
            KEY_CATEGORY to PARAM_CATEGORY,
            KEY_QUERY to "$q $TRANSFERS",
            KEY_LANGUAGE to language
        )
        return service.fetchLatestTransferNews(MAP_HEADERS_NEWS, queryMap)
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.results?.let {
                    return@map it
                }
                throw Exception("List is null")
            }
            .doOnError { errorTransformer.convertThrowableForNewsData(it) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}