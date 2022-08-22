package com.transfers.transfertracker.util.errors

object GenericCodes {
    const val CODE_INTERNET_CONNECTION = 100
    const val CODE_INTERNAL_ERROR = 101
    const val CODE_UNKNOWN = -1
}
object AuthCodes {
    const val CONST_EMAIL_ALREADY_EXIST = "ERROR_EMAIL_ALREADY_IN_USE"
    const val CONST_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val CONST_INVALID_EMAIL = "ERROR_INVALID_EMAIL"
    const val CONST_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD"
    const val CONST_INVALID_PASSWORD = "ERROR_INVALID_PASSWORD"
    const val CONST_INTERNAL_ERROR = "ERROR_INTERNAL_ERROR"
    const val CONST_INVALID_ARGUMENT = "ERROR_INVALID_ARGUMENT"
    const val CONST_INVALID_PHONE_NUMBER = "ERROR_INVALID_PHONE_NUMBER"

    const val CODE_EMAIL_ALREADY_EXIST = 407
    const val CODE_USER_NOT_FOUND = 401
    const val CODE_INVALID_EMAIL = 403
    const val CODE_WRONG_PASSWORD = 405
    const val CODE_INVALID_PASSWORD = 404
    const val CODE_INVALID_ARGUMENT = 402
    const val CODE_INVALID_PHONE_NUMBER = 406
}

object FBCodes{
    const val CODE_INTERNAL_CODE = 500
    const val CODE_CLIENT_CLOSED = 499
    const val CODE_BACKEND_FAILURE = 204
    const val CODE_CLIENT_CANCELED = 205
    const val CODE_OK = 200
}

object NewsCodes {
    const val CODE_MISSING_PARAMETER = 400
    const val CODE_UNAUTHORIZED = 401
    const val CODE_RESTRICTED = 403
    const val CODE_DUPLICATE_PARAMETER = 409
    const val CODE_UNSUPPORTED_TYPE = 415
    const val CODE_UNPROCESSABLE_ENTITY = 422
    const val CODE_MAX_REQUEST = 429
    const val CODE_BACKEND_FAILURE = 500
}