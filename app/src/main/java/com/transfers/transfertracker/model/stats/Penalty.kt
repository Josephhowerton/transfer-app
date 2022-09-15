package com.transfers.transfertracker.model.stats

data class Penalty(
    val missed: Missed?,
    val scored: Scored?,
    val total: Int?
)