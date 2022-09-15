package com.transfers.transfertracker.model.stats

data class Minute(
    val `0-15`: MinutesInterval?,
    val `106-120`: MinutesInterval?,
    val `16-30`: MinutesInterval?,
    val `31-45`: MinutesInterval?,
    val `46-60`: MinutesInterval?,
    val `61-75`: MinutesInterval?,
    val `76-90`: MinutesInterval?,
    val `91-105`: MinutesInterval?
)