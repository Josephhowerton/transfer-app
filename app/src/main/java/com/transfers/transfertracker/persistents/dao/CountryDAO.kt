package com.transfers.transfertracker.persistents.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.transfers.transfertracker.model.country.Country
import io.reactivex.rxjava3.core.Single

@Dao
interface CountryDAO {

    @Insert
    fun saveCountry(country: Country)

    @Insert
    fun saveCountries(country: List<Country>)

    @Query("select * from country where name=:name")
    fun getPlayerCountry(name: String): Single<Country>

    @Query("select * from country")
    fun getAllCountries(): Single<List<Country>>

    @Query("delete from country")
    fun invalidateCache()
}