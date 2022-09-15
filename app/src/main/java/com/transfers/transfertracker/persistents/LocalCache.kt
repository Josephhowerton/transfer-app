package com.transfers.transfertracker.persistents

import androidx.room.Database
import androidx.room.RoomDatabase
import com.transfers.transfertracker.model.country.Country
import com.transfers.transfertracker.persistents.LocalCache.Companion.DATABASE_VERSION
import com.transfers.transfertracker.persistents.dao.CountryDAO

@Database(entities = [Country::class], version = DATABASE_VERSION, exportSchema = false)
abstract class LocalCache: RoomDatabase() {
    companion object{
        const val DATABASE_VERSION = 1
    }

    abstract fun getCountryDAO(): CountryDAO
}