package com.transfers.transfertracker.model.tranfers

data class TransferResponse(
    val errors: Any?,
    val response: List<TransferData>,
)