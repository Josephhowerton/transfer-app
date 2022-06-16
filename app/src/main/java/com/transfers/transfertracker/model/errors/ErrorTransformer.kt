package com.transfers.transfertracker.model.errors

import com.google.firebase.auth.FirebaseAuthException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

class ErrorTransformer {

    fun convertThrowableForAuthentication(exception: Exception): AuthException {

        if(exception is IOException){
            return if(exception is UnknownHostException || exception is ConnectException){
                AuthException(AuthError.ERROR_INTERNET_CONNECTION)
            }else{
                AuthException(AuthError.ERROR_INTERNAL_ERROR)
            }
        }

        if(exception is FirebaseAuthException){
            return when(exception.errorCode){
                AuthCodes.ERROR_INTERNAL_ERROR -> AuthException(AuthError.ERROR_INTERNAL_ERROR)
                AuthCodes.ERROR_USER_NOT_FOUND -> AuthException(AuthError.ERROR_USER_NOT_FOUND)
                AuthCodes.ERROR_INVALID_ARGUMENT -> AuthException(AuthError.ERROR_INVALID_ARGUMENT)
                AuthCodes.ERROR_INVALID_EMAIL -> AuthException(AuthError.ERROR_INVALID_EMAIL)
                AuthCodes.ERROR_INVALID_PASSWORD -> AuthException(AuthError.ERROR_INVALID_PASSWORD)
                AuthCodes.ERROR_WRONG_PASSWORD -> AuthException(AuthError.ERROR_WRONG_PASSWORD)
                AuthCodes.ERROR_INVALID_PHONE_NUMBER -> AuthException(AuthError.ERROR_INVALID_PHONE_NUMBER)
                AuthCodes.ERROR_EMAIL_ALREADY_EXIST -> AuthException(AuthError.ERROR_EMAIL_ALREADY_EXIST)
                else -> AuthException(AuthError.ERROR_UNKNOWN)
            }
        }

        return AuthException(AuthError.ERROR_UNKNOWN)
    }
}