package com.transfers.transfertracker.view.auth.viewmodel

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.transfers.transfertracker.R
import com.transfers.transfertracker.repo.AuthRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import com.transfers.transfertracker.util.errors.AuthError
import com.transfers.transfertracker.util.errors.AuthException
import com.transfers.transfertracker.util.result.AuthResultError
import com.transfers.transfertracker.util.result.AuthResultSuccess
import com.transfers.transfertracker.util.result.BaseAuthResult
import com.transfers.transfertracker.util.result.BaseResult
import com.transfers.transfertracker.view.auth.AuthSuccessUserView
import com.transfers.transfertracker.view.auth.ResetSuccessUserView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(private val repository: AuthRepository): ViewModel(), DefaultLifecycleObserver, SubscribeOnLifecycle{
    private lateinit var compositeDisposable: CompositeDisposable

    private val _result = MutableLiveData<BaseAuthResult>()
    val result: LiveData<BaseAuthResult> = _result

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        compositeDisposable = CompositeDisposable()
    }
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        compositeDisposable.dispose()
    }

    fun isUserLoggedIn() : Boolean = FirebaseAuth.getInstance().currentUser != null

    override fun subscribeOnLifecycle(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    fun validateSignIn(emailField: String, passwordField: String): Boolean = isEmailValid(emailField) && isPasswordValid(passwordField)

    fun validateSignUp(nameField: String, emailField: String, passwordField: String): Boolean =
        isNameValid(nameField) && isEmailValid(emailField) && isPasswordValid(passwordField)

    fun isEmailValid(emailField: String): Boolean = emailField.isNotBlank() && emailField.contains("@") && emailField.contains(".")

    private fun isNameValid(nameField: String) : Boolean = nameField.isNotBlank() && nameField.matches(Regex("[a-zA-Z]+"))

    private fun isPasswordValid(password: String): Boolean = password.isNotBlank() && password.length > 5

    fun signIn(emailField: String, passwordField: String) {
        subscribeOnLifecycle(
            repository.signIn(emailField, passwordField)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError{
                    repository.signOut()
                }
                .subscribe(
                    {
                        when(it){
                            is BaseResult.Success -> _result.value = AuthResultSuccess(success = AuthSuccessUserView(displayName = ""))
                            is BaseResult.Error -> _result.value = handleAuthError(R.string.error_sign_in_failed, it)
                            else -> _result.value = AuthResultError(R.string.error_sign_in_failed, R.string.error_default)
                        }
                    },
                    {
                        _result.value = handleAuthError(R.string.error_sign_in_failed, BaseResult.Error(it))
                    }
                )
        )
    }

    fun signUp(displayNameField: String, emailField: String, passwordField: String) {
        subscribeOnLifecycle(
            repository.signUp(displayNameField, emailField, passwordField)
                .subscribe(
                    {
                        if(it is BaseResult.Success){
                            _result.value = AuthResultSuccess(success = AuthSuccessUserView(displayName = displayNameField))
                        }
                    },
                    {
                        _result.value = handleAuthError(R.string.error_sign_up_failed, BaseResult.Error(it))
                    }
                )
        )
    }

    fun sendResetPasswordEmail(emailField: String) {
        subscribeOnLifecycle(repository.sendPasswordResetEmail(emailField)
            .subscribe(
                {
                    if(it is BaseResult.Success){
                        if(it.data){
                            _result.value = AuthResultSuccess(success = ResetSuccessUserView(R.string.error_check_email_title, R.string.error_check_email_message))
                        }
                    }
                },
                {
                    _result.value = handleAuthError(R.string.error_could_not_send_password_reset_email, BaseResult.Error(it))
                }
            )
        )
    }

    private fun handleAuthError(title: Int, baseResult: BaseResult.Error): BaseAuthResult {
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