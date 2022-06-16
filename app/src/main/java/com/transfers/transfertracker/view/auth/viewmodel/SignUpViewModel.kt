package com.transfers.transfertracker.view.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transfers.source.MainRepository

class SignUpViewModel(mainRepository: MainRepository) : ViewModel(){

    val displayNameField = MutableLiveData<String>()
    val emailField = MutableLiveData<String>()
    val passwordField = MutableLiveData<String>()

    val signUpButtonEnabled: LiveData<Boolean> = passwordField.map {
        validateSignUpData()
    }

    private val _signUpResult = MutableLiveData<BaseAuthResult>()
    val signUpResult: LiveData<BaseAuthResult> = _signUpResult

    val showProgressBar = MutableLiveData(View.GONE)

    fun signUp() {
        subscribeOnLifecycle(
            userAuthRepository.signUp(displayNameField.value!!, emailField.value!!, passwordField.value!!)
            .doOnSubscribe{ showProgressBar.value = View.VISIBLE }
            .subscribe(
                {
                    showProgressBar.value = View.GONE
                    if(it is BaseResult.Success){
                        if(displayNameField.value is String){
                            _signUpResult.value = AuthResultSuccess(success = AuthSuccessUserView(displayName = displayNameField.value!!))
                        }
                    }
                },
                {
                    showProgressBar.value = View.GONE
                    _signUpResult.value = handleAuthError(R.string.error_sign_up_failed, BaseResult.Error(it))
                }
            )
        )
    }

    private fun validateSignUpData() : Boolean {
        return displayNameField.value != null && isEmailValid(emailField.value) && isPasswordValid(emailField.value)
    }

    private fun isEmailValid(username: String?): Boolean {
        return if (!username.isNullOrBlank() && username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        }else {
            false
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return !password.isNullOrBlank() && password.length > 5
    }
}