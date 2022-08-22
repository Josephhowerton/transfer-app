package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.network.CountryService
import com.transfers.transfertracker.source.CountryDataSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CountryDataSourceImpl @Inject constructor(private val service: CountryService): CountryDataSource {

    override fun fetchAllCountries(): Single<List<Country>> =
        service.fetchAllCountries(API_FOOTBALL_HEADER_MAP)
            .map { it.response }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    override fun fetchCountryByCode(code: String): Single<List<Country>> =
        service.fetchCountryByCode(API_FOOTBALL_HEADER_MAP, code)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    override fun fetchCountryByName(name: String): Single<List<Country>> =
        service.fetchCountryByName(API_FOOTBALL_HEADER_MAP, name)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    override fun fetchCountriesBySearch(query: String): Observable<List<Country>> =
        service.fetchCountriesBySearch(API_FOOTBALL_HEADER_MAP, query)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}