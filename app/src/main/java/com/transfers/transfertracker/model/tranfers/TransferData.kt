package com.transfers.transfertracker.model.tranfers

data class TransferData(
    val player: Player?,
    val transfers: List<Transfer>,
    val update: String?
)