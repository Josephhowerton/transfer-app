package com.transfers.transfertracker.model.errors

class FBException(private val authError: FBError) : Throwable() {
    fun getAuthError() = authError
}