package com.transfers.transfertracker.util.errors

import com.transfers.transfertracker.util.NetworkUtil

class ErrorTransformer {

    fun convertThrowableForFB(exception: Throwable): FBException {
        return when(NetworkUtil.getStatusCode(exception)){
            GenericCodes.CODE_INTERNET_CONNECTION -> FBException(FBError.ERROR_INTERNET_CONNECTION)
            GenericCodes.CODE_INTERNAL_ERROR -> FBException(FBError.ERROR_INTERNAL_ERROR)
            FBCodes.CODE_INTERNAL_CODE -> FBException(FBError.ERROR_INTERNAL_CODE)
            FBCodes.CODE_CLIENT_CLOSED -> FBException(FBError.ERROR_CLIENT_CLOSED)
            FBCodes.CODE_BACKEND_FAILURE -> FBException(FBError.ERROR_BACKEND_FAILURE)
            FBCodes.CODE_CLIENT_CANCELED -> FBException(FBError.ERROR_CLIENT_CANCELED)
            else -> FBException(FBError.ERROR_UNKNOWN)
        }
    }

    fun convertThrowableForAuthentication(exception: Throwable): AuthException {
        return when(NetworkUtil.getStatusCode(exception)){
            GenericCodes.CODE_INTERNET_CONNECTION -> AuthException(AuthError.ERROR_INTERNET_CONNECTION)
            GenericCodes.CODE_INTERNAL_ERROR -> AuthException(AuthError.ERROR_INTERNAL_ERROR)
            AuthCodes.CODE_USER_NOT_FOUND -> AuthException(AuthError.ERROR_USER_NOT_FOUND)
            AuthCodes.CODE_INVALID_ARGUMENT -> AuthException(AuthError.ERROR_INVALID_ARGUMENT)
            AuthCodes.CODE_INVALID_EMAIL -> AuthException(AuthError.ERROR_INVALID_EMAIL)
            AuthCodes.CODE_INVALID_PASSWORD -> AuthException(AuthError.ERROR_INVALID_PASSWORD)
            AuthCodes.CODE_WRONG_PASSWORD -> AuthException(AuthError.ERROR_WRONG_PASSWORD)
            AuthCodes.CODE_INVALID_PHONE_NUMBER -> AuthException(AuthError.ERROR_INVALID_PHONE_NUMBER)
            AuthCodes.CODE_EMAIL_ALREADY_EXIST -> AuthException(AuthError.ERROR_EMAIL_ALREADY_EXIST)
            else -> AuthException(AuthError.ERROR_UNKNOWN)
        }
    }

    fun convertThrowableForNewsData(exception: Throwable): NewsException {
        return when(NetworkUtil.getStatusCode(exception)){
            GenericCodes.CODE_INTERNET_CONNECTION -> NewsException(NewsDataError.ERROR_INTERNET_CONNECTION)
            GenericCodes.CODE_INTERNAL_ERROR -> NewsException(NewsDataError.ERROR_INTERNAL_ERROR)
            NewsCodes.CODE_MISSING_PARAMETER -> NewsException(NewsDataError.ERROR_MISSING_PARAMETER)
            NewsCodes.CODE_UNAUTHORIZED -> NewsException(NewsDataError.ERROR_UNAUTHORIZED)
            NewsCodes.CODE_RESTRICTED -> NewsException(NewsDataError.ERROR_RESTRICTED)
            NewsCodes.CODE_DUPLICATE_PARAMETER -> NewsException(NewsDataError.ERROR_DUPLICATE_PARAMETER)
            NewsCodes.CODE_UNSUPPORTED_TYPE -> NewsException(NewsDataError.ERROR_UNSUPPORTED_TYPE)
            NewsCodes.CODE_UNPROCESSABLE_ENTITY -> NewsException(NewsDataError.ERROR_UNPROCESSABLE_ENTITY)
            NewsCodes.CODE_MAX_REQUEST -> NewsException(NewsDataError.ERROR_MAX_REQUEST)
            NewsCodes.CODE_BACKEND_FAILURE -> NewsException(NewsDataError.ERROR_BACKEND_FAILURE)
            else -> NewsException(NewsDataError.ERROR_UNKNOWN)
        }
    }
}