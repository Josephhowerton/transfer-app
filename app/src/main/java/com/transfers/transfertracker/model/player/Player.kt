package com.transfers.transfertracker.model.player

data class Player(
    val age: Int,
    val id: Int,
    var teamId: String?,
    val name: String,
    val number: Int,
    val photo: String,
    val position: String
)