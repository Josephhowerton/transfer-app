package com.transfers.transfertracker.model.result

data class AuthResultError(val errorTitle: Int, val errorMessage: Int): BaseAuthResult()