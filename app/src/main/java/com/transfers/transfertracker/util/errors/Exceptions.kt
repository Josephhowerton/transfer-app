package com.transfers.transfertracker.util.errors

import com.transfers.transfertracker.model.User

data class AuthException(val authError: AuthError, val user: User? = null) : Throwable()
data class NewsException(val error: NewsDataError) : Throwable()
data class FBException(val authError: FBError) : Throwable()