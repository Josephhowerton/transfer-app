package com.transfers.transfertracker.model.country

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey
    val name: String,
    val code: String?,
    val flag: String?
)