package com.transfers.transfertracker.model.tranfers

import com.transfers.transfertracker.enums.ETransfer

data class Player(
    val id: Int?,
    val name: String?,
    var teamId: String?,
    var photo: String?,
    var transfer: ETransfer?
)