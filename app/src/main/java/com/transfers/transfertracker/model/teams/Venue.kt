package com.transfers.transfertracker.model.teams

data class Venue(
    val venueId: Int?,
    var teamOwnerId: Int?,
    val address: String?,
    val capacity: Int?,
    val city: String?,
    val image: String?,
    val name: String?,
    val surface: String?
)