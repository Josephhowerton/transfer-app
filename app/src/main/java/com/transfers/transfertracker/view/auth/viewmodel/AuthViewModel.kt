package com.transfers.transfertracker.view.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.transfers.source.impl.MainRepositoryImpl
import javax.inject.Inject

class AuthViewModel @Inject constructor(repository: MainRepositoryImpl): ViewModel() {

    public fun isUserLoggedIn() : Boolean = true

    protected fun handleAuthError(title: Int, baseResult: BaseResult.Error): BaseAuthResult {
        val exception = if(baseResult.exception is AuthException) {
            baseResult.exception
        } else{
            AuthException(AuthError.ERROR_UNKNOWN)
        }

        return when (exception.authError) {
            AuthError.ERROR_INVALID_ARGUMENT -> AuthResultError(
                title,
                R.string.error_invalid_argument
            )

            AuthError.ERROR_USER_NOT_FOUND -> AuthResultError(
                title,
                R.string.error_user_not_found
            )

            AuthError.ERROR_INVALID_EMAIL -> AuthResultError(
                title,
                R.string.error_invalid_email
            )

            AuthError.ERROR_WRONG_PASSWORD -> AuthResultError(
                title,
                R.string.error_wrong_password
            )

            AuthError.ERROR_INVALID_PASSWORD -> AuthResultError(
                title,
                R.string.error_invalid_password
            )

            AuthError.ERROR_INVALID_PHONE_NUMBER -> AuthResultError(
                title,
                R.string.error_invalid_phone_number
            )

            AuthError.ERROR_EMAIL_ALREADY_EXIST -> AuthResultError(
                title,
                R.string.error_email_already_exist
            )

            AuthError.ERROR_INTERNET_CONNECTION -> AuthResultError(
                title,
                R.string.error_internet_connection
            )

            else -> AuthResultError(
                title,
                R.string.error_default
            )
        }
    }
}