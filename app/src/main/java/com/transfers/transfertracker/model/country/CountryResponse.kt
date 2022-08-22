package com.transfers.transfertracker.model.country

data class CountryResponse(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: List<Any>,
    val response: List<Country>,
    val results: Int
)