package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.country.Country
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CountryDataSource {
    fun fetchAllCountries(): Single<List<Country>>
    fun fetchCountryByCode(code: String): Single<List<Country>>
    fun fetchCountryByName(name: String): Single<List<Country>>
    fun fetchCountriesBySearch(query: String): Observable<List<Country>>
}