package com.transfers.transfertracker.network.service

import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.model.country.CountryResponse
import com.transfers.transfertracker.network.CountryEndpoints.KEY_CODE
import com.transfers.transfertracker.network.CountryEndpoints.KEY_NAME
import com.transfers.transfertracker.network.CountryEndpoints.KEY_SEARCH
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface CountryService {

    @GET("/countries")
    fun fetchAllCountries(@HeaderMap headerMap: Map<String, String>) : Single<CountryResponse>

    @GET("/countries")
    fun fetchCountryByCode(@HeaderMap headerMap: Map<String, String>, @Query(KEY_CODE) code: String) : Single<List<Country>>

    @GET("/countries")
    fun fetchCountryByName(@HeaderMap headerMap: Map<String, String>, @Query(KEY_NAME) name: String) : Single<List<Country>>

    @GET("/countries")
    fun fetchCountriesBySearch(@HeaderMap headerMap: Map<String, String>, @Query(KEY_SEARCH) query: String) : Observable<List<Country>>

}