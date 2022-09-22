package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.util.result.BaseResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CountryDataSource {
    fun getAllCountries(): Single<List<Country>>
    fun fetchAllCountries(): Single<List<Country>>
    fun fetchCountryByCode(code: String): Single<List<Country>>
    fun fetchCountryByName(name: String): Single<List<Country>>
    fun fetchCountriesBySearch(query: String): Observable<List<Country>>
    fun getPlayerCountry(name: String) : Single<Country>
}