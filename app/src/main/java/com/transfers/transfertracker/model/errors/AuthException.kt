package com.transfers.transfertracker.model.errors

import com.transfers.transfertracker.model.User

data class AuthException(val authError: AuthError, val user: User? = null) : Throwable()