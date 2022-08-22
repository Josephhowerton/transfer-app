package com.transfers.transfertracker.util

import com.google.firebase.auth.FirebaseAuthException
import com.transfers.transfertracker.util.errors.AuthCodes
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_EMAIL_ALREADY_EXIST
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_INVALID_ARGUMENT
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_INVALID_EMAIL
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_INVALID_PASSWORD
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_INVALID_PHONE_NUMBER
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_USER_NOT_FOUND
import com.transfers.transfertracker.util.errors.AuthCodes.CODE_WRONG_PASSWORD
import com.transfers.transfertracker.util.errors.GenericCodes
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

object NetworkUtil {

    fun getStatusCode(exception: Throwable): Int =
        when(exception){
            is HttpException -> exception.code()
            is ConnectException -> GenericCodes.CODE_INTERNET_CONNECTION
            is UnknownHostException -> GenericCodes.CODE_INTERNET_CONNECTION
            is IOException -> GenericCodes.CODE_INTERNAL_ERROR
            is FirebaseAuthException -> getStatusCodeForFirebaseException(exception)
            else -> GenericCodes.CODE_UNKNOWN
        }

    private fun getStatusCodeForFirebaseException(exception: FirebaseAuthException): Int {
        return when(exception.errorCode){
            AuthCodes.CONST_INTERNAL_ERROR -> GenericCodes.CODE_INTERNAL_ERROR
            AuthCodes.CONST_USER_NOT_FOUND -> CODE_USER_NOT_FOUND
            AuthCodes.CONST_INVALID_ARGUMENT -> CODE_INVALID_ARGUMENT
            AuthCodes.CONST_INVALID_EMAIL -> CODE_INVALID_EMAIL
            AuthCodes.CONST_INVALID_PASSWORD -> CODE_INVALID_PASSWORD
            AuthCodes.CONST_WRONG_PASSWORD -> CODE_WRONG_PASSWORD
            AuthCodes.CONST_INVALID_PHONE_NUMBER -> CODE_INVALID_PHONE_NUMBER
            AuthCodes.CONST_EMAIL_ALREADY_EXIST -> CODE_EMAIL_ALREADY_EXIST
            else -> GenericCodes.CODE_UNKNOWN
        }
    }

}